(ns todo.handlers.todo_test
  (:require [todo.handlers.todo :refer :all]
            [todo.domain.todo :as todo]
            [ring.mock.request :as mock]
            [clojure.test :refer :all]))

(defn in?
  "true if coll contains elm"
  [coll elm]
  (some #(= elm %) coll))

(deftest todo-router
  (let [todos (atom (todo/new-list))
        get-todos (fn [] @todos)
        add-todo (fn [desc] (swap! todos todo/add desc))
        toggle-todo (fn [id] (do (println "SWAPPING") (swap! todos todo/toggle id)))
        todo-router (new-router get-todos add-todo toggle-todo)]
    (testing "index page"
      (let [response (todo-router (mock/request :get "/"))]
        (is (= 200 (:status response)))))
    (testing "add new todo"
      (let [response (todo-router (->
                                    (mock/request :post "/todos")
                                    (mock/content-type "application/x-www-form-urlencoded")
                                    (mock/body "todo-name=new todo")))]
        (is (= 200 (:status response)))
        (is (in? (-> (get-todos) todo/remove-ids) {:name "new todo" :done false}))))
    (testing "toggle todo"
      (let [first-item (-> (get-todos) first :id)
            response (todo-router (->
                                    (mock/request :post (str "/todos/" first-item "/toggle"))))]
        (println (get-todos))
        (is (= 200 (:status response)))
        (is (in? (-> (get-todos) todo/remove-ids) {:name "Learn Clojure" :done true})))) ))

