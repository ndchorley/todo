(ns todo.handlers.todo_test
  (:require [todo.handlers.todo :refer :all]
            [todo.domain.todo :refer :all]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]))

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(deftest todo-router
  (let [todos (new-todo-list)
        get-todos (fn [] @todos)
        add-todo (fn [desc] (swap! todos add-new desc))
        todo-router (new-router get-todos add-todo)]
    (testing "index page"
      (let [response (todo-router (mock/request :get "/"))]
        (is (= 200 (:status response)))))
    ;(testing "some other page that should 404 to check my sanity (it returns nil because 404-ness is done by some other middleware)"
    ;  (let [response (todo-router (-> mock/request :get "/some-other-page"))]
    ;    (is (= 404 (:status response)))))
    (testing "add new todo"
      (let [response (todo-router (->
                                    (mock/request :post "/todos")
                                    (mock/content-type "application/x-www-form-urlencoded")
                                    (mock/body "todo-name=new todo")))]
        (is (= 200 (:status response)))
        (is (in? (get-todos) {:name "new todo" :done false}))))))

