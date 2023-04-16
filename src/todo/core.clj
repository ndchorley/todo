(ns todo.core
  (:require
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [ring.middleware.defaults :refer :all]
    [todo.views.todo :refer :all]
    [todo.handlers.todo :refer :all]
    [todo.domain.todo :refer :all])
  (:gen-class))

(def todos (new-todo-list))
(def get-todos (fn [] @todos))
(defn add-todo [description]
  (swap! todos add-new description))

(defroutes myapp
           (GET "/" [] (render-index @todos))
           (POST "/todos" _ (handle-new-todo get-todos add-todo)))

(defn -main []
  (run-server
    (wrap-defaults myapp (assoc site-defaults :security false))
    {:port 5001}))