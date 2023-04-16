(ns todo.core
  (:require
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [ring.middleware.defaults :refer :all]
    [todo.views.todo :refer :all]
    [todo.handlers.todo :refer :all])
  (:gen-class))

(def todos (atom [
                  {:name "Learn Clojure" :done false}
                  {:name "Buy Gin" :done false}
                  ]))

(defn add-todo [new-todo]
  (swap! todos (fn [todos] (conj todos {:name new-todo :done false}))))

(defroutes myapp
           (GET "/" [] (-> @todos render-index))
           (POST "/todos" req (handle-new-todo req @todos add-todo)))

(defn -main []
  (run-server
    (wrap-defaults myapp (assoc site-defaults :security false))
    {:port 5001}))