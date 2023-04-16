(ns todo.handlers.todo_test
  (:require [todo.handlers.todo :refer :all]
            [todo.domain.todo :refer :all]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]))

(deftest todo-router
  (let [todos (new-todo-list)
        get-todos (fn [] @todos)
        add-todo (fn [desc] (swap! todos add-new desc))
        todo-router (new-router get-todos add-todo)]
    (testing "index page"
      (is (= (:status (todo-router (mock/request :get "/"))) 200)))
    (testing "add new todo"
      (is (= (:status (todo-router (mock/request :post "/todos" {:params {:todo-name "new todo"}}))) 200)))))