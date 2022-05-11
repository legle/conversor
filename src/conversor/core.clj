(ns conversor.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def opcoes-do-programa [["-d" "--de Moeda base" "Moeda base para conversão" :default "brl"]
                         ["-p" "--para moeda destino" "Moeda a qual queremos saber o valor" :default "usd"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (prn "As opções são:"
       (:options (parse-opts args opcoes-do-programa))))