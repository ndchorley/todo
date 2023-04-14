(ns todo.core
  (:require
   [ring.adapter.jetty :refer [run-jetty]]
   [hiccup.page :as page]
   [hiccup.core :refer :all])
  (:gen-class))

(def todos (atom [
                  {:name "Learn Clojure" :done false}
                  {:name "Buy beer" :done false}
                  ]))


(defn render-todos-fragment [todos]
  [:div {:id "todos"}
   (map
     (fn [todo] [:h1 (todo :name)])
     todos)
   ])

(defn render-whole-page [todos]
  (page/html5
    [:body
     [:script {:src "https://unpkg.com/htmx.org@1.9.0" :crossorigin "anonymous"}]
      (render-todos-fragment todos)
     [:form {:hx-post "/todos" :hx-target "#todos"}
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
      (if (= (request :request-method) :post)
        (do
          (swap! todos
                 (fn [todos] (conj todos {:name (request :body) :done false}))) ;Nicky, i don't know how to get form data, but this "works"
          {:status 200 :body (html (render-todos-fragment (deref todos)))})

        {:status 200 :body (render-whole-page (deref todos))}))
    {:port 8080}))
