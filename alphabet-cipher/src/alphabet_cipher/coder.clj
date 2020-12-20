(ns alphabet-cipher.coder
  (:require [clojure.string :refer :all])
  )

(defn resize-keyword [keyword message]
  (apply str (take (count message) (cycle keyword))))

(defn get-index [c str]
  (inc (index-of str c)))

(defn rotate [s n]
  (if (> n 0)
    (rotate (apply str (concat (rest s) (list (first s)))) (dec n))
    s))

(defn encode [keyword message]
  (comment
    (def alphabet "abcdefghijklmnopqrstuvwxyz")
    (def m "m")
    (def k "s")
    (def rotations (dec (get-index m alphabet)))
    (def index (get-index k alphabet))
    (def keyword "s")
    (def message "m"))
  (let [alphabet "abcdefghijklmnopqrstuvwxyz"
        keyword (resize-keyword keyword message)]
    (apply str
           (for [[k m] [(map str keyword message)]]
             (let [index (get-index k alphabet)
                   rotations (dec (get-index m alphabet))]
               (nth (rotate alphabet rotations) (dec index))))
           )
    ))

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

