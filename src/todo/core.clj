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
        edit-todo (fn [id new-status new-name] (swap! todos todo/edit id new-status new-name))
        delete-todo (fn [id] (swap! todos todo/delete id))]
    (run-server
      (new-router get-todos add-todo edit-todo delete-todo)
      {:port 5001})))
