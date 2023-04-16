(ns todo.domain.todo_test
  (:require [todo.domain.todo :refer :all]
            [clojure.test :refer :all]))

(deftest manage-a-todo-list
  (testing "new-todo-list"
    (is (= (new-todo-list) [{:name "Learn Clojure" :done false}
                            {:name "Buy Gin" :done false}])))
  (testing "add-new"
    (is (= (add-new (new-todo-list) "new todo")
           [{:name "Learn Clojure" :done false}
            {:name "Buy Gin" :done false}
            {:name "new todo" :done false}])))
  (testing "mark-done"
    (is (= (mark-done (new-todo-list) "Learn Clojure")
           [{:name "Learn Clojure" :done true}
            {:name "Buy Gin" :done false}]))))