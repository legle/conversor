(ns conversor.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :refer [upper-case]]
            [cheshire.core :refer [parse-string]]
            [clj-http.client :as http-client])
  (:gen-class))

(def api-url "https://economia.awesomeapi.com.br/json/last")

(def opcoes-do-programa [["-d" "--de Moeda base" "Moeda base para conversão" :default "brl"]
                         ["-p" "--para moeda destino" "Moeda a qual queremos saber o valor" :default "usd"]])

(defn parametrizar-moedas
  [de para]
  (upper-case (str de "-" para)))

(defn raiz-do-json
  [de para]
  (upper-case (str de para)))

(defn pegar-valor
  [json chave]
  (get-in json [chave]))

(defn compor-url
  [moedas]
  (str api-url "/" moedas))

(defn consultar-cotacao!
  [de para]
  (-> (:body (http-client/get (compor-url (parametrizar-moedas de para))))
      (parse-string)
      (get-in [(raiz-do-json de para)])))

(defn formatar-resultado
  [json]
  (str "A cotação de " (pegar-valor json "name") " é " (pegar-valor json "bid")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [de para]} (:options (parse-opts args opcoes-do-programa))]
    (prn (formatar-resultado (consultar-cotacao! de para)))))
