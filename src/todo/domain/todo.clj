(ns todo.domain.todo
  [:require [clojure.string :as str]])

(defn- new-todo [name]
  {:name name :done false :id (str (random-uuid))})
(defn new-list [] [(new-todo "Learn Clojure")
                        (new-todo "Buy Gin")])

(defrecord Todo [name done id])

(defn add [todos description]
  (conj todos (new-todo description)))

(defn edit [todos id new-status new-name]
  (map (fn [t] (if (= (:id t) id) (assoc t :done new-status :name new-name) t)) todos))

(defn remove-ids [todos]
  (map #(dissoc % :id) todos))

(defn find-by-id [todos id]
  (first (filter #(= (:id %) id) todos)))

(defn search [todos search-term]
  (filter #(let [name-lower (clojure.string/lower-case (:name %))
                 search-lower (clojure.string/lower-case search-term)]
             (.contains name-lower search-lower))
          todos))

(defn delete [todos id]
  (remove #(= (:id %) id) todos))
