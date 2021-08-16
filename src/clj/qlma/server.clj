(ns qlma.server
  (:require [ring.adapter.jetty :as jetty]
            [qlma.handler :as handler]))

(defonce server (atom nil))

(defn stop! []
  (swap! server #(.stop %)))

(defn -main [& args]
  "Start server from REPL by running (-main)"
  (if-not @server
    (reset! server (jetty/run-jetty #'handler/app {:port 3000 :join? false}))
    (println "Already started")))

