(ns todo.handlers.todo
  (:use ring.util.response)
  [:require [todo.views.todo :as view]
            [todo.domain.todo :as todo]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]])

(defn handle-new-todo [get-todos, add-todo]
  (fn [req] (let [new-todo (-> req :params :todo-name)
                  is-htmx (get-in req [:headers "hx-request"])]
              (do
                (add-todo new-todo)
                (if is-htmx
                  (view/todos-fragment (get-todos))
                  (redirect "/"))))))

(defn- id-from-request [req]
  (-> req :params :id))

(defn handle-patch-todo [get-todos, edit-todo]
  (fn [req] (let [new-status (-> req :params :done parse-boolean)
                  new-name (-> req :params :name)
                  id (id-from-request req)
                  is-htmx (get-in req [:headers "hx-request"])]
              (do
                (edit-todo id new-status new-name)
                (if is-htmx
                  (view/todo-fragment (todo/find-by-id (get-todos) id))
                  (redirect "/"))))))

(defn handle-get-todos [get-todos]
  (fn [req] (let [search (-> req :params (get :search ""))
                  is-htmx (get-in req [:headers "hx-request"])
                  todos (todo/search (get-todos) search)]
              (if is-htmx
                (view/todos-fragment todos)
                (view/index todos)))))

(defn handle-delete-todo [delete-todo]
  (fn [req] (let [id (id-from-request req)
                  is-htmx (get-in req [:headers "hx-request"])]
              (do
                (delete-todo id)
                (if is-htmx
                  (str "")
                  (redirect "/"))))))

(defn handle-get-todo [get-todos]
  (fn [req] (let [id (id-from-request req)
                  todo (todo/find-by-id (get-todos) id)]
              (do
                (println "id" id)
                (view/todo-form todo)))))

(defn new-router [get-todos add-todo edit-todo delete-todo]
  (wrap-defaults
    (defroutes router
               (GET "/" [] (view/index (get-todos)))
               (GET "/static/styles.css" [] {:status 200 :headers {"Content-Type" "text/css"} :body view/css})
               (GET "/todos" _ (handle-get-todos get-todos))
               (POST "/todos" _ (handle-new-todo get-todos add-todo))
               (PATCH "/todos/:id" _ (handle-patch-todo get-todos edit-todo))
               (GET "/todos/:id" _ (handle-get-todo get-todos))
               (POST "/todos/:id/delete" _ (handle-delete-todo delete-todo))
               (POST "/todos/:id/edit" _ (handle-patch-todo get-todos edit-todo))
               (DELETE "/todos/:id" _ (handle-delete-todo delete-todo)))
    (assoc site-defaults :security false)))

