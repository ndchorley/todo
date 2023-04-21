(ns todo.views.todo
  (:require [hiccup.page :as page]
            [hiccup.core :refer :all]))

(defn- render-todo [todo]
  (do
    [:li
     [:form {:method "POST" :action (str "/todos/" (todo :id) "/delete")}
      [:button {:hx-target "closest li" :hx-swap "outerHTML" :hx-delete (str "/todos/" (todo :id))} "❌"]]
     [:form {:method "GET" :action (str "/todos/" (todo :id))}
      [:button {:hx-target "closest li" :hx-swap "outerHTML" :hx-get (str "/todos/" (todo :id))} "\uD83D\uDCDD"]]
     [:form
      {:hx-swap   "outerHTML"
       :hx-target "closest li"
       :class     (when (todo :done) "done")}
      [:input {:type "hidden" :name "done" :value (str (not (todo :done)))}]
      [:input {:type "hidden" :name "name" :value (todo :name)}]
      [:span {:hx-patch (str "/todos/" (todo :id))} (todo :name)]]
     ]))

(defn todo-form [todo]
  (html
    [:li
     [:button {:disabled true} "❌"]
     [:button {:disabled true} "📝"]
     [:form {:hx-patch (str "/todos/" (todo :id)), :hx-target "closest li", :hx-swap "outerHTML" :method "POST" :action (str "/todos/" (todo :id) "/edit")}
      [:input {:type "hidden" :name "done" :value (str (todo :done))}]
      [:input {:type "text", :name "name" :value (todo :name)} ]
      [:input {:type "submit"}]]
     ]))

(defn todo-fragment [todo] (-> todo render-todo html))

(defn render-todos [todos]
  [:ul {:id "todos"}
   (map render-todo todos)])

(defn todos-fragment [todos] (-> todos render-todos html))

(defn- render-search []
  [:form {:method "GET" :action "/todos"}
   [:label "Search "
    [:input
     {:type "text", :name "search", :placeholder "Begin Typing To Search", :hx-get "/todos", :hx-trigger "keyup changed delay:500ms, search", :hx-target "#todos", :hx-replace "innerHTML"}]]])

(defn index [todos]
  (page/html5
    [:body
     [:script {:src "https://unpkg.com/htmx.org@1.9.0" :crossorigin "anonymous"}]
     [:link {:rel "stylesheet" :href "/static/styles.css"}]
     [:section
      [:h1 "TODO"]
      (render-search)
      (render-todos todos)
      [:form {:hx-post "/todos" :hx-target "#todos" :method "post" :action "/todos"}
       [:input {:type "text" :name "todo-name"}]
       [:input {:type "submit"}]]]
     ]
    )
  )

(def css "
body {
    font-family: 'Courier Prime', monospace;
    font-size: 20px;
    background-color: lightyellow;
}

section {
    width: 500px;
    margin: 1em auto;
}

h1 {
    text-align: center;
    font-size: 5em;
    margin: 0;
    padding: 0;
}

li {
    padding: 1em 0 1em 0;
    border-bottom: 4px dotted darkred;
    display: block;
}

ul {
  padding:0;
  margin: 0;
  display: block;
  list-style: none;
}

label input {
    width: 100%;
    margin-left: 1em;
}

label {
   display: flex;
}

button {
    all: unset;
    cursor: pointer;
    font-size: 0.6em;
    margin-right:1em;
}

button:focus {
    outline: red 5px auto;
}

form {
    display: inline;
}

.done {
    text-decoration: line-through;
}")
