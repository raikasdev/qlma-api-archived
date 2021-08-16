(ns qlma.db.users
  (:require [qlma.db.core :refer :all]
            [buddy.hashers :as password]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/users.sql")

(defn get-all-users []
  (select-all-users db-spec))

(defn create-user
  [{:keys [username firstname lastname password]}]
  (insert-new-user<! db-spec username firstname lastname (password/encrypt password)))

(defn- get-user-password
  "Get user password from DB"
  [username]
  (-> (select-user-password db-spec username) first :password))

(defn- get-user-data [username]
  (dissoc (first (select-user-data db-spec username)) :password))

(defn get-my-user-data
  [{:keys [password username]}]
  (if (password/check password (get-user-password username))
    (select-keys (get-user-data username) [:firstname
                                           :lastname
                                           :id])))
(defn valid-user?
  "Check that username and password match"
  [{:keys [password username]}]
  (if (password/check password (get-user-password username))
    true
    false))

