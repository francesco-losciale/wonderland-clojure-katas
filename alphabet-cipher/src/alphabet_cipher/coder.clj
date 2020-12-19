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
  (let [alphabet "abcdefghijklmnopqrstuvwxyz"
        keyword (resize-keyword keyword message)]

    )
  "encodeme")

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

