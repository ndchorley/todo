(ns todo.handlers.todo
  [:require [todo.views.todo :as view]
            [todo.domain.todo :as todo]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]])

(defn handle-new-todo [get-todos, add-todo]
  (fn [req] (let [new-todo (-> req :params :todo-name)]
              (do
                (add-todo new-todo)
                (view/todos-fragment (get-todos))))))

(defn handle-patch-todo [get-todos, todo-status]
  (fn [req] (let [new-status (-> req :params :done parse-boolean)
                     id (-> req :params :id)]
              (do
                (todo-status id new-status)
                (view/todo-fragment (todo/find-by-id (get-todos) id))))))

(defn handle-delete-todo [delete-todo]
  (fn [req] (let [id (-> req :params :id)]
              (do
                (delete-todo id)
                (str "")))))

(defn new-router [get-todos add-todo todo-status delete-todo]
  (wrap-defaults
    (defroutes router
               (GET "/" [] (view/index (get-todos)))
               (GET "/static/styles.css" [] {:status 200 :headers {"Content-Type" "text/css"} :body view/css})
               (POST "/todos" _ (handle-new-todo get-todos add-todo))
               (PATCH "/todos/:id" _ (handle-patch-todo get-todos todo-status))
               (DELETE "/todos/:id" _ (handle-delete-todo delete-todo)))
    (assoc site-defaults :security false)))

