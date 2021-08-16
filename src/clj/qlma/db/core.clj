(ns qlma.db.core
  (:require [qlma.settings :as settings]
            [environ.core :refer [env]]))

(def db-spec (settings/get-settings))
