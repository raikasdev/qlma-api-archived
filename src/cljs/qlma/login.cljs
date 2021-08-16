(ns qlma.login
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer (chan put! <!)]
            [qlma.app :as app]
            [reagent.core :as r]))

(defn input-element
  "An input element which updates its value on change"
  [id name type value]
  [:input {:id        id
           :name      name
           :class     "form-input"
           :type      type
           :required  ""
           :value     @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn username-input
  [username-input-atom]
  (input-element "username" "username" "text" username-input-atom))

(defn password-input
  [password-input-atom]
  (input-element "password" "password" "password" password-input-atom))


(defn form []
  (let [username (r/atom nil)
        password (r/atom nil)]
    (fn []
      [:div {:id "login-container"}
       [:img {:id    "logo"
              :src   "img/qlma.png"
              :width 150}]
       [:div {:id "form-area"}
        [:form
         [:span {:class "form-text"} "KÄYTTÄJÄTUNNUS"]
         [username-input username]
         [:span {:class "form-text"} "SALASANA"]
         [password-input password]
         [:input
          {:class "form-button" :type "submit" :value "KIRJAUDU" :onClick (fn [event] (. event preventDefault) (put! app/EVENTCHANNEL [:login [@username @password]]))}]
         [:div "username " @username]

         [:a
          {:href "#/" :id "unohtuiko-text"}
          "UNOHTUIKO SALASANA"]
         ]]
       [:div {:id "school-text"}
        [:span (str (:schoolname @app/app-state))]]])))
