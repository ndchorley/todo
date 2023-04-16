(ns todo.handlers.todo
  [:require [todo.views.todo :refer :all]])

(defn render-index [todos] (render-whole-page todos))

; todo / design question. It sucks that this thing has to "know" about todo being an atom
(defn handle-new-todo [todos, add-todo]
  (fn [req] (let [new-todo (:todo-name (:params req))]
              (do
                (add-todo todos new-todo)
                (render-todos-fragment @todos)))))