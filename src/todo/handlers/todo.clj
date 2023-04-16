(ns todo.handlers.todo
  [:require [todo.views.todo :refer :all]])

(defn render-index [todos] (render-whole-page todos))

(defn handle-new-todo [req, todos, add-todo]
  (let [new-todo (:todo-name (:params req))]
    (do
      (add-todo new-todo)
      (render-todos-fragment todos))))