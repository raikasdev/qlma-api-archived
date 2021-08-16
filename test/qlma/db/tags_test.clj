(ns qlma.db.tags-test
  (:require [clojure.test :refer :all]
            [qlma.db.users :as users]
            [qlma.db.tags :as tags]
            [utils.database.migrations :as mig]
            [qlma.db.core :refer :all]
            [yesql.core :refer [defqueries]]))

(defqueries "queries/tests.sql")

(def first-user-id
  (atom 0))

(def personal-tag
  (atom 0))

(def global-tag
  (atom 0))


(deftest test-tags

  (testing "Add users"
    (is (reset! first-user-id (:id (users/create-user {:username "mattitag"
                                                       :password "matti1"
                                                       :firstname "Matti"
                                                       :lastname "Mattila"})))))

  (testing "Insert new global tag"
    (is (= 6 (count (tags/create-global-tag "Global test tag")))))

  (testing "Insert another new global tag"
    (is (reset! global-tag (:id (tags/create-global-tag "Another global test tag")))))

  (testing "Insert new personal tag"
    (is (reset! personal-tag (:id (tags/create-personal-tag @first-user-id "Personal test tag")))))

  (testing "Check global tags"
    (is (= 2 (count (tags/get-global-tags)))))

  (testing "Check personal tags"
    (is (= 1 (count (tags/get-personal-tags @first-user-id)))))

  (testing "Check global tag with id"
    (is (= 1 (count (tags/get-tag @global-tag @first-user-id)))))

  (testing "Check personal tag with id"
    (is (= 1 (count (tags/get-tag @personal-tag @first-user-id)))))

)

(use-fixtures :once
  (fn [f]
    (mig/migrate)
    (f)))

