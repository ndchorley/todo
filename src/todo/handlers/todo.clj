(ns todo.handlers.todo
  [:require [todo.views.todo :refer :all]
            [todo.domain.todo :as todo]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer :all]])

(defn handle-new-todo [get-todos, add-todo]
  (fn [req] (let [new-todo (-> req :params :todo-name)]
              (do
                (add-todo new-todo)
                (render-todos-fragment (get-todos))))))

(defn handle-toggle-todo [get-todos, toggle-todo]
  (fn [req] (let [id (-> req :params :id)]
              (do
                (toggle-todo id)
                (render-todo-fragment (todo/find-by-id (get-todos) id))))))

(defn new-router [get-todos add-todo toggle-todo]
  (wrap-defaults
    (defroutes router
               (GET "/" [] (render-whole-page (get-todos)))
               (GET "/static/styles.css" [] {:status 200 :headers {"Content-Type" "text/css"} :body css})
               (POST "/todos" _ (handle-new-todo get-todos add-todo))
               (POST "/todos/:id/toggle" _ (handle-toggle-todo get-todos toggle-todo)))
    (assoc site-defaults :security false)))

