(ns todo.domain.todo)

(defn new-todo-list []
  (atom [{:name "Learn Clojure" :done false}
         {:name "Buy Gin" :done false}]))

(defn add-todo [todos new-todo]
  (swap! todos (fn [todos] (conj todos {:name new-todo :done false}))))