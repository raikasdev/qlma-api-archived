(ns qlma.db.users-test
  (:require [clojure.test :refer :all]
            [qlma.db.users :as users]
            [utils.database.migrations :as mig]
            [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/tests.sql")

(deftest test-add-user
  (testing "Check if database is clean"
    (is (= 0 (count (users/get-all-users)))))

  (testing "Add user to database"
    (is (= 7 (count (users/create-user {:username "woltage"
                                        :password "jeejee"
                                        :lastname "iiro"
                                        :firstname "matti"})))))

  (testing "Password and username match"
    (is (= 3 (count (users/get-my-user-data {:username "woltage"
                                             :password "jeejee"})))))

  (testing "Check user found from db"
    (is (= 1 (count (users/get-all-users))))))

(defn- clean-database []
  (delete-all-messages! db-spec)
  (delete-all-tags! db-spec)
  (delete-all-users! db-spec))

(use-fixtures :once
  (fn [f]
    (mig/migrate)
    (clean-database)
    (f)))

