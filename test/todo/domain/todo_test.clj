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
  (testing "edit"
    (let [todos (todo/new-list)]
      (is (= (todo/remove-ids (todo/edit todos (:id (first todos)) true "Learn Scheme"))
             [{:name "Learn Scheme" :done true}
              {:name "Buy Gin" :done false}]))))
  (testing "find-by-id"
    (let [todos (todo/new-list)]
      (is (= (todo/find-by-id todos (:id (first todos)))
             {:name "Learn Clojure" :done false :id (:id (first todos))}))))
  (testing "delete-by-id"
    (let [todos (todo/new-list)]
      (is (= (todo/delete todos (:id (first todos)))
             [{:name "Buy Gin" :done false :id (:id (second todos))}]))))
  (testing "search"
    (let [todos (todo/new-list)]
      (is (= (todo/remove-ids (todo/search todos "gin"))
             [{:name "Buy Gin" :done false}])))))