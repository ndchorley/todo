(ns todo.views.todo
  (:require [hiccup.page :as page]
            [hiccup.core :refer :all]))

(defn- render-todo [todo]
  (do
      [:li
       {:hx-swap   "outerHTML"
        :hx-post   (str "/todos/" (todo :id) "/toggle")
        :class     (when (todo :done) "done")}
       (todo :name)
       ]))

(defn todo-fragment [todo] (-> todo render-todo html))

(defn render-todos [todos]
  [:div {:id "todos"}
   (map render-todo todos)])

(defn todos-fragment [todos] (-> todos render-todos html))

(defn index [todos]
  (page/html5
    [:body
     [:script {:src "https://unpkg.com/htmx.org@1.9.0" :crossorigin "anonymous"}]
     [:link {:rel "stylesheet" :href "/static/styles.css"}]
     [:h1 "TODO"]
     (render-todos todos)
     [:form {:hx-post "/todos" :hx-target "#todos"}
      [:input {:type "text" :name "todo-name"}]
      [:input {:type "submit"}]]
     ]
    )
  )

(def css "
body {
    font-family: 'Courier Prime', monospace;
    font-size: 20px;
    background-color: lightyellow;
}

h1 {
    text-align: center;
    font-size: 5em;
    margin: 0;
    padding: 0;
}

.done {
    text-decoration: line-through;
}")