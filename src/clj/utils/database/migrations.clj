(ns utils.database.migrations
  (:require [migratus.core :as migratus]
            [qlma.settings :as settings]))

(defn config []
  {:store                 :database
   :migration-dir         "migrations/"
   :migration-table-name  "migration"
   :db (settings/get-settings)})

(defn migrate []
  (migratus/migrate (config)))

(defn create [name]
  {:pre [(string? name)]}
  (when name
    (migratus/create (config) name)))
