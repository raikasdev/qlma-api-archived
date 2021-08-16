(ns qlma.db.messages
  (:require [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]
            [clj-time.format :as f]
            [clj-time.coerce :as c]))

(defqueries "queries/messages.sql")

(def finnish-time-format (f/formatter "dd.MM.yyyy HH:mm:ss"))

(defn get-all-messages []
  (select-all-messages db-spec))

(defn get-message [id my-id]
  (let [messages (select-message-with-id db-spec id my-id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     messages)))

(defn get-replies [id my-id]
  (let [messages (select-replies-to-message db-spec id my-id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     messages)))

(defn get-messages-to-user [user_id]
  (let [messages (select-messages-to-user db-spec user_id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     messages)))

(defn get-messages-from-user [user_id]
  (let [messages (select-messages-from-user db-spec user_id)
        parse-fn (comp (partial f/unparse finnish-time-format) c/from-sql-date)]
    (map
     #(-> %
          (update :create_time parse-fn)
          (update :edit_time parse-fn))
     messages)))

(defn send-message
  ([from to message subject]
   (send-message from to message subject nil))
  ([from to message subject parent_id]
   (insert-new-message<! db-spec from to message subject parent_id)))
