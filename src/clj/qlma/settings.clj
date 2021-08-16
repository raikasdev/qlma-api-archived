(ns qlma.settings)

;; Funktio kopioitu
;; https://github.com/Opetushallitus/clojure-utils/blob/master/src/clj/oph/common/infra/asetukset.clj
(defn read-settings-from-file
  "Lue asetukset tiedostosta"
  [path]
  (try
    (with-open [reader (clojure.java.io/reader path)]
      (doto (java.util.Properties.)
        (.load reader)))
    (catch java.io.FileNotFoundException _
      {})))

;; Funktio kopioitu
;; https://github.com/Opetushallitus/clojure-utils/blob/master/src/clj/oph/common/infra/asetukset.clj
(defn dotkeys->tree-map [m]
  (reduce #(let [[k v] %2
                 path (map keyword (.split (name k) "\\."))]
             (assoc-in %1 path v))
          {}
          m))

(defn get-settings
  ([] (get-settings "app.properties"))
  ([file]
   (let [get-settings-from-file (dotkeys->tree-map (read-settings-from-file file))]
     (if (empty? get-settings-from-file)
       {:classname "org.postgresql.Driver",
        :subprotocol "postgresql",
        :subname "//localhost:5432/qlma",
        :user "qlma"
        :password "qlma"
        :secret-key "NOT IN PRODUCTION!"}
       (get-settings-from-file)))))
