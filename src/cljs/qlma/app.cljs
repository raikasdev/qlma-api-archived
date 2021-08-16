(ns qlma.app
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [secretary.core :refer [dispatch!]]
            [cljs.core.async :refer (chan put! <!)]))

(defonce app-state
         (r/atom
           {:token      ""
            :schoolname "Sylvään koulu, Sastamala"
            :username   "Testi Esa"
            :message    "Please login"
            :page       :home}))

(def EVENTCHANNEL (chan))


(def EVENTS
  {:update-username (fn [username]
                      (swap! app-state assoc :username username))
   :login           (fn [data]
                      (let [[username password] data]
                        (js/console.log "username:" username)
                        (js/console.log "password:" password)
                        (go (let [response (<! (http/post "http://localhost:3000/api/v1/login" {:json-params {:username username :password password}}))]
                              (swap! app-state assoc :token (:token (:body response)))
                              (let [response (:status response)]
                                (js/console.log "Got response: " response)
                                (if (= response 200) (put! EVENTCHANNEL [:navigate :qlma]) (put! EVENTCHANNEL [:navigate :login])))))))

   :navigate        (fn [page]
                      (js/console.log "Navigating to page: " page)
                      (swap! app-state assoc :page page))})

(go
  (while true
    (let [[event-name event-data] (<! EVENTCHANNEL)]
      ((event-name EVENTS) event-data))))
