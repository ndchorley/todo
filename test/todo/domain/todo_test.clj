(ns todo.domain.todo_test
  (:require [todo.domain.todo :as todo]
            [clojure.test :refer :all]))

(deftest manage-a-todo-list
  (testing "new-todo-list"
    (is (= (todo/remove-ids (todo/new-list)) [{:name "Learn Clojure" :done false}
                            {:name "Buy Gin" :done false}])))
  (testing "add-new"
    (is (= (-> (todo/new-list) (todo/add "new todo") todo/remove-ids)
           [{:name "Learn Clojure" :done false}
            {:name "Buy Gin" :done false}
            {:name "new todo" :done false}])))
  (testing "toggle"
    (let [todos (todo/new-list)]
      (is (= (todo/remove-ids (todo/toggle todos (:id (first todos))))
             [{:name "Learn Clojure" :done true}
              {:name "Buy Gin" :done false}]))))
  (testing "find-by-id"
    (let [todos (todo/new-list)]
      (is (= (todo/find-by-id todos (:id (first todos)))
             {:name "Learn Clojure" :done false :id (:id (first todos))})))))