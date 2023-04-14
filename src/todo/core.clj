(ns todo.core
    (:require
   [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (run-jetty (fn [request] {:status 200 :body "Hello"}) {:port 8080}))
