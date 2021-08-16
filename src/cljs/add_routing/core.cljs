(ns add_routing.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [qlma.app :as app]
            [cljs.core.async :refer (chan put! <!)]))


(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
            (put! app/EVENTCHANNEL [:navigate :home]))

  (defroute "/login" []
            (put! app/EVENTCHANNEL [:navigate :login]))

  (defroute "/qlma" []
            (put! app/EVENTCHANNEL [:navigate :qlma]))

  (hook-browser-navigation!))
