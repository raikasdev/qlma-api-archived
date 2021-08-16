(defproject qlma "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://www.qlma.fi"

  :source-paths ["src/clj"]
  :resource-paths ["resources"]

  :min-lein-version "2.0.0"
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [yesql "0.4.2"]
                 [migratus "0.8.32"]
                 [hiccup "1.0.5"]
                 [metosin/ring-swagger "0.22.11"]
                 [buddy "0.7.2"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [ring/ring-json "0.4.0"]
                 [environ "1.0.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-cors "0.1.8"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-jetty-adapter "1.5.0"]

                 ; Metosin <3
                 [metosin/compojure-api "1.1.8"]
                 [metosin/ring-swagger "0.22.11"]
                 [metosin/ring-swagger-ui "2.2.2-0"]
                 [metosin/ring-http-response "0.8.0"]

                 [prismatic/schema "1.1.3"]
                 [org.clojure/clojurescript "1.8.51"
                  :scope "provided"]
                 [org.clojure/core.async "0.2.391"]
                 [reagent "0.6.0"]
                 [reagent-forms "0.5.25"]
                 [reagent-utils "0.2.0"]
                 [secretary "1.2.3"]
                 [cljs-http "0.1.42"]]
  :plugins [
            [lein-ring "0.9.7"]
            [lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.8"]]

  :cljsbuild {
              :builds [
                       { :id "dev"
                         :source-paths ["src/cljs"]
                         :figwheel true
                         :compiler { :main "qlma.core"
                                     :output-to    "resources/public/js/app.js"
                                     :output-dir "resources/public/js/out"
                                     :asset-path "js/out"
                                     :optimizations :none
                                     :pretty-print  true}}]
              }


  :figwheel { :http-server-root "public"
              :server-port 5309
              :css-dirs ["resources/public/css"]
              :ring-handler qlma.handler/app}

  :ring { :handler qlma.handler/app
          :init    utils.database.migrations/migrate}

  :profiles { :dev { :dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring-mock "0.1.5"]]}})
