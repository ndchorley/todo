(ns todo.core
  (:require
    [compojure.core :refer :all]
    [org.httpkit.server :refer [run-server]]
    [todo.views.todo :refer :all]
    [todo.handlers.todo :refer :all]
    [todo.domain.todo :as todo])
  (:gen-class))

; todo: not sure how best to capture this atom-ness and servicey things outside of code for now
; this is kinda bad, and you'll see is repeated in the handler test
; but it's also nice to push impure stuff like this to the edges, need to have a think
(def todos (atom (todo/new-list)))
(def get-todos (fn [] @todos))
(defn add-todo [description]
  (swap! todos todo/add description))
(defn toggle-todo [id]
  (swap! todos todo/toggle id))

(defn -main []
  (run-server
    (new-router get-todos add-todo toggle-todo)
    {:port 5001}))