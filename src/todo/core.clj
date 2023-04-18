(ns todo.core
  (:require
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [todo.views.todo :refer :all]
    [todo.handlers.todo :refer :all]
    [todo.domain.todo :as todo])
  (:gen-class))

(defn -main []
  (let [todos (atom (todo/new-list))
        get-todos (fn [] @todos)
        add-todo (fn [desc] (swap! todos todo/add desc))
        todo-status (fn [id new-status] (swap! todos todo/new-status id new-status))
        delete-todo (fn [id] (swap! todos todo/delete id))]
    (run-server
      (new-router get-todos add-todo todo-status delete-todo)
      {:port 5001})))
