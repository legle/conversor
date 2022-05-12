(ns conversor.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [cheshire.core :refer [parse-string]]
            [clj-http.client :as http-client])
  (:gen-class))

(def api-url "https://economia.awesomeapi.com.br/json/last")

(def opcoes-do-programa [["-d" "--de Moeda base" "Moeda base para conversão" :default "brl"]
                         ["-p" "--para moeda destino" "Moeda a qual queremos saber o valor" :default "usd"]])

(defn parametrizar-moedas
  [de para]
  (str de "-" para))

(defn compor-url
  [moedas]
  (str api-url "/" moedas))

(defn consultar-cotacao!
  [de para]
  (-> (:body (http-client/get (compor-url (parametrizar-moedas de para))))
      (parse-string)
      (get-in ["BRLUSD" "ask"])))

(defn formatar-resultado
  [cotacao]
  (prn cotacao)
  (str "A cotação da moeda é " cotacao))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [de para]} (:options (parse-opts args opcoes-do-programa))]
    (prn (formatar-resultado (consultar-cotacao! de para)))))
