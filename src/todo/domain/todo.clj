(ns todo.domain.todo)

(defn- new-todo [name]
  {:name name :done false :id (str (random-uuid))})
(defn new-todo-list [] [(new-todo "Learn Clojure")
                        (new-todo "Buy Gin")])

(defn add-new [todos description]
  (conj todos (new-todo description)))

(defn toggle [todos id]
  (do (println (str "toggling " id))
      (println "uuid?" (uuid? id))
      (map (fn [t] (if (= (:id t) id) (assoc t :done (not (:done t))) t)) todos)))

(defn remove-ids [todos]
  (map #(dissoc % :id) todos))