(ns todo.views.todo
  (:require     [hiccup.page :as page]
                [hiccup.core :refer :all]))

(defn render-todos [todos]
  [:div {:id "todos"}
   (map
     (fn [todo] [:h1 (todo :name)])
     todos)
   ])

(defn render-todos-fragment [todos] (-> todos render-todos html))

(defn render-whole-page [todos]
  (page/html5
    [:body
     [:script {:src "https://unpkg.com/htmx.org@1.9.0" :crossorigin "anonymous"}]
     (render-todos todos)
     [:form {:hx-post "/todos" :hx-target "#todos"}
      [:input {:type "text" :name "todo-name"}]
      [:input {:type "submit"}]]
     ]
    )
  )