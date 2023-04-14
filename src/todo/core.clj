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
     [:script {:src "https://unpkg.com/htmx.org@1.9.0" :crossorigin "anonymous"}]
      (map
        (fn [todo] [:h1 (todo :name)])
        todos)
     [:form {:hx-post "/todos"}
      [:input {:type "text" :name "todo-name"}]
      [:input {:type "submit"}]]
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
