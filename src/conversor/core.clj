(ns conversor.core
  (:gen-class))

(defn valores-em
  [argumento]
  (cond
    (.startsWith argumento "--de=")
    {:de (.substring argumento 5)}
    (.startsWith argumento "--para=")
    {:para (.substring argumento 7)}
    :else {}))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Temos " (count args) " Argumentos")
  (println (map valores-em args)))