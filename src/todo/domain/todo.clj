(ns todo.domain.todo)

(defn new-todo-list [] [{:name "Learn Clojure" :done false}
                        {:name "Buy Gin" :done false}])

(defn add-new [todos new-todo]
  (conj todos {:name new-todo :done false}))
