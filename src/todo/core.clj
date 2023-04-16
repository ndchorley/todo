(ns todo.core
  (:require
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [todo.views.todo :refer :all]
    [todo.handlers.todo :refer :all]
    [todo.domain.todo :refer :all])
  (:gen-class))

(def todos (new-todo-list))
(def get-todos (fn [] @todos))
(defn add-todo [description]
  (swap! todos add-new description))

(defn -main []
  (run-server
    (new-router get-todos add-todo)
    {:port 5001}))