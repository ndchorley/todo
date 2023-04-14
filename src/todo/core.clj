(ns todo.core
  (:require
   [hiccup.page :as page]
   [hiccup.core :refer :all]
   [compojure.core :refer :all]
   [org.httpkit.server :refer [run-server]]
   [ring.middleware.defaults :refer :all])
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

(defroutes myapp
           (GET "/" [] (render-whole-page (deref todos)))
           (POST "/todos" req
             (let [new-todo (get (:params req) :todo-name)]
               (do
                 (println new-todo)
                 (swap! todos (fn [todos] (conj todos {:name new-todo :done false})))
                 (html (render-todos-fragment (deref todos)))))))

(defn -main []
  (run-server
    (wrap-defaults myapp (assoc site-defaults :security false))
    {:port 5001}))