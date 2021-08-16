(ns qlma.db.tags
  (:require [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]
            [clj-time.format :as f]
            [clj-time.coerce :as c]))

(defqueries "queries/tags.sql")

(def finnish-time-format (f/formatter "dd.MM.yyyy HH:mm:ss"))

(defn get-all-tags []
  (select-all-tags db-spec))

(defn get-tag [id my-id]
  (let [tags (select-tag-with-id db-spec id my-id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     tags)))

(defn get-personal-tags [user_id]
  (let [tags (select-personal-tags db-spec user_id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     tags)))

(defn get-global-tags []
  (let [tags (select-global-tags db-spec)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     tags)))


(defn create-personal-tag [user_id tagname]
  (insert-personal-tag<! db-spec tagname user_id)
)

(defn create-global-tag [tagname]
  (insert-global-tag<! db-spec tagname)
)
