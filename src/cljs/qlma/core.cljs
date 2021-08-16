(ns qlma.core
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:import goog.History)
  (:require [reagent.core :as r]
            [qlma.login :as login]
            [qlma.app :as app]
            [add_routing.core :as ad]
            [cljs.core.async :refer (chan put! <!)]))

(defn home []
  [:div [:h1 "Home Page"]
   [:a {:href "#/login"} "login page"]])

(defn qlma []
  [:div [:h1 "QLMA LOGGED IN "]
   [:a {:href "#/login"} "login page"]])

(defmulti current-page #(@app/app-state :page))
(defmethod current-page :home []
  [home])
(defmethod current-page :qlma []
  [qlma])
(defmethod current-page :login []
  (login/form))
(defmethod current-page :default []
  [:div])

(defn ^:export init []
  (ad/app-routes)
  (r/render [current-page]
            (.getElementById js/document "app")))
