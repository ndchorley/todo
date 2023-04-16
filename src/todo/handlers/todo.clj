(ns todo.handlers.todo
  [:require [todo.views.todo :refer :all]
            [compojure.core :refer :all]])

(defn render-index [todos] (render-whole-page todos))

(defn handle-new-todo [get-todos, add-todo]
  (fn [req] (let [new-todo (:todo-name (:params req))]
              (do
                (add-todo new-todo)
                (render-todos-fragment (get-todos))))))

(defn new-router [get-todos add-todo]
  (defroutes router
             (GET "/" [] (render-index (get-todos)))
             (POST "/todos" _ (handle-new-todo get-todos add-todo))))