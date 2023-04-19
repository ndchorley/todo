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

(defn- id-from-request [req]
  (-> req :params :id))

(defn handle-patch-todo [get-todos, todo-edit]
  (fn [req] (let [new-status (-> req :params :done parse-boolean)
                  new-name (-> req :params :name)
                  id (id-from-request req)]
              (do
                (todo-edit id new-status new-name)
                (view/todo-fragment (todo/find-by-id (get-todos) id))))))

(defn handle-delete-todo [delete-todo]
  (fn [req] (let [id (id-from-request req)]
              (do
                (delete-todo id)
                (str "")))))

(defn handle-get-todo [get-todos]
  (fn [req] (let [id (id-from-request req)]
              (do
                (println "id" id)
                (view/todo-form (todo/find-by-id (get-todos) id))))))

(defn new-router [get-todos add-todo todo-edit delete-todo]
  (wrap-defaults
    (defroutes router
               (GET "/" [] (view/index (get-todos)))
               (GET "/static/styles.css" [] {:status 200 :headers {"Content-Type" "text/css"} :body view/css})
               (POST "/todos" _ (handle-new-todo get-todos add-todo))
               (PATCH "/todos/:id" _ (handle-patch-todo get-todos todo-edit))
               (GET "/todos/:id" _ (handle-get-todo get-todos))
               (DELETE "/todos/:id" _ (handle-delete-todo delete-todo)))
    (assoc site-defaults :security false)))

