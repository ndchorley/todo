(ns todo.core
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [hiccup.page :as page])
  (:gen-class))

(def todos [
  {:name "Learn Clojure" :done false}
  {:name "Buy beer" :done false}
])

(defn render [todos]
  (page/html5
    [:body
      (map
        (fn [todo] [:h1 (todo :name)])
        todos)
    ]
  )
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (run-jetty 
    (fn [request] 
    {:status 200 :body (render todos)}) 
    {:port 8080}))
