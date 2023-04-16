(ns todo.handlers.todo
  [:require [todo.views.todo :refer :all]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]])

(defn handle-new-todo [get-todos, add-todo]
  (fn [req] (let [new-todo (-> req :params :todo-name)]
              (do
                (add-todo new-todo)
                (render-todos-fragment (get-todos))))))

(defn new-router [get-todos add-todo]
  (wrap-defaults
    (defroutes router
               (GET "/" [] (render-whole-page (get-todos)))
               (POST "/todos" _ (handle-new-todo get-todos add-todo)))
    (assoc site-defaults :security false)))
