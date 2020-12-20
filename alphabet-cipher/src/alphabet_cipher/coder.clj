(ns alphabet-cipher.coder
  (:require [clojure.string :refer :all])
  )

(defn resize-keyword [keyword message]
  (apply str (take (count message) (cycle keyword))))

(defn get-index [c str]
  (index-of str c))

(defn rotate [s n]
  (if (> n 0)
    (rotate (apply str (concat (rest s) (list (first s)))) (dec n))
    s))

(defn encode [keyword message]
  (comment
    (def alphabet "abcdefghijklmnopqrstuvwxyz")
    (def m "m")
    (def k "s")
    (def rotations (get-index m alphabet))
    (def index (get-index k alphabet))
    (def keyword "scones")
    (def adjusted-keyword (resize-keyword keyword message))
    (def message "meetmebythetree"))
  (let [alphabet "abcdefghijklmnopqrstuvwxyz"
        adjusted-keyword (resize-keyword keyword message)]
    (apply str
           (for [[k m] (map str adjusted-keyword message)]
             (let [index (get-index k alphabet)
                   rotations (get-index m alphabet)]
               (nth (rotate alphabet rotations) index))
             )
           )
    ))

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

