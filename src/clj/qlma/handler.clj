(ns qlma.handler
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [ring.middleware.json :as middleware]
            [schema.core :as s]
            [ring.util.response :as resp]
            [clj-time.core :as time]
            [buddy.sign.jws :as jws]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.backends.token :refer [jws-backend]]
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth.accessrules :as acl]
            [qlma.db.users :as user]
            [qlma.db.messages :as messages]
            [qlma.settings :as settings]
            [ring.middleware.cors :as cors]))

(def secret (:secret-key (settings/get-settings)))

(def auth-backend (jws-backend {:secret secret
                                :options {:alg :hs512}}))

(defn permission-denied
  ([] (permission-denied "Permission denied"))
  ([message]
   (unauthorized message)))

(def req-headers
  { "Access-Control-Allow-Origin" "*"
   "Access-Control-Allow-Headers" "Content-Type"
   "Access-Control-Allow-Methods" "GET,POST,OPTIONS"
   "Content-Type" "application/json; charset=utf-8" })

(defn login [user]
  (let [username (:username user)
        password (:password user)
        valid? (user/valid-user? {:username username, :password password})]
    (if valid?
      (let [user-data (user/get-my-user-data {:password password
                                              :username username})
            session-data (merge {:username (keyword username)
                                 :exp (time/plus (time/now) (time/seconds 3600))}
                                user-data)
            token (jws/sign session-data secret {:alg :hs512})]
        (ok {:token token}))
      (permission-denied))))

(defn any-user
  [req]
  (acl/success))

(defn logged-user
  [req]
  (if (authenticated? req)
    true
    (acl/error (permission-denied))))

(defn on-error
  [request value]
  {:status 403
   :headers req-headers
   :body value})

(def rules [{:pattern #"^/api/v1/(?!login$).*"
             :handler logged-user}
            {:uri "/api/v1/login"
             :handler any-user}])

(s/defschema NewLogin
  {:username s/Str
   :password s/Str})

(defroutes app-routes
  (GET "/" []
    (resp/content-type
      (resp/resource-response "index.html" {:root "public"}) "text/html")))

(def app-routes
  (api
    {:swagger
      {:ui "/api-docs"
       :spec "/swagger.json"
       :data {:info {:title "Sample API"
                     :description "Compojure Api example"}
              :tags [{:name "api", :description "some apis"}]}}}

    (GET "/" []
      (resp/content-type
        (resp/resource-response "index.html" {:root "public"}) "text/html"))
    (context "/api/v1" []
      :responses {401 {:description "Permission denied"}}
      (POST "/login" []
        :body [user NewLogin]
        :summary "Login"
        (login user))
      (context "/messages" []
               :tags ["Messages"]
               :responses {401 {:description "Permission denied"}}
               :header-params [authorization :- (describe String "Token")]
        (GET "/" request
          :summary "Get all messages for logged user"
          (let [my-id (-> request :identity :id)]
            (ok (messages/get-messages-to-user my-id))))
        (POST "/" request
          :summary "Send message"
          :body-params [to :- (describe Integer "Message to")
                        subject :- (describe String "Subject")
                        message :- (describe String "Message")]
          (let [my-id (-> request :identity :id)]
            (ok (messages/send-message my-id to message subject))))
        (GET "/sent" request
          :summary "Get all sended mesages"
          (let [my-id (-> request :identity :id)]
                     (ok (messages/get-messages-from-user my-id))))
        (context "/:message-id" []
          :path-params [message-id :- (describe Integer "Get message with id")]
          (GET "/" request
            :summary "Get message with ID"
            (let [my-id (-> request :identity :id)]
              (ok (messages/get-message message-id my-id))))
          (GET "/replies" request
            :summary "Get reply messages"
            (let [my-id (-> request :identity :id)]
              (ok (messages/get-replies message-id my-id))))))
      (context "/profile" []
        :tags ["Profile"]
        :responses {401 {:description "Permission denied"}}
        :header-params [authorization :- (describe String "Token")]
        (GET "/" request
          (let [info (-> request :indentity)]
            (ok info)))))
    (undocumented
      (route/resources "/")
      (route/not-found "404 Not found"))))

(def app
  (->
   app-routes
   (acl/wrap-access-rules {:rules rules})
   (wrap-authentication auth-backend)
   (wrap-defaults api-defaults)))
