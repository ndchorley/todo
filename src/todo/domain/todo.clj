(ns todo.domain.todo)

(defn- new-todo [name]
  {:name name :done false :id (str (random-uuid))})
(defn new-list [] [(new-todo "Learn Clojure")
                        (new-todo "Buy Gin")])

(defn add [todos description]
  (conj todos (new-todo description)))

(defn toggle [todos id]
  (map (fn [t] (if (= (:id t) id) (assoc t :done (not (:done t))) t)) todos))

(defn new-status [todos id new-status]
  (map (fn [t] (if (= (:id t) id) (assoc t :done new-status) t)) todos))

(defn remove-ids [todos]
  (map #(dissoc % :id) todos))

(defn find-by-id [todos id]
  (first (filter #(= (:id %) id) todos)))

(defn delete [todos id]
  (remove #(= (:id %) id) todos))
