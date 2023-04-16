(ns todo.core
  (:require
    [hiccup.core :refer :all]
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [ring.middleware.defaults :refer :all]
    [todo.views.todo :refer :all])
  (:gen-class))

(def todos (atom [
                  {:name "Learn Clojure" :done false}
                  {:name "Buy Gin" :done false}
                  ]))

(defn add-todo [new-todo]
  (swap! todos (fn [todos] (conj todos {:name new-todo :done false}))))

(defroutes myapp
           (GET "/" [] (render-whole-page (deref todos)))
           (POST "/todos" req
             (let [new-todo (:todo-name (:params req))]
               (do
                 (add-todo new-todo)
                 (-> @todos render-todos-fragment html)))))

(defn -main []
  (run-server
    (wrap-defaults myapp (assoc site-defaults :security false))
    {:port 5001}))