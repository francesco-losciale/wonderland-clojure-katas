(ns magic-square.puzzle
  (:require [clojure.math.combinatorics :as combo]))

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(defn sum-cols [m]
  [(reduce + (map first m))
   (reduce + (map second m))
   (reduce + (map last m))])

(defn sum-diagonals [m]
  [(+ (get-in m [0 0]) (get-in m [1 1]) (get-in m [2 2]))
   (+ (get-in m [2 0]) (get-in m [1 1]) (get-in m [0 2]))])

(defn sum-rows [m]
  (map #(reduce + %) m))

(defn make-square [values]
  (vec (map vec (partition 3 values))))

(defn magic-square? [values]
  (comment
    (def values [8 1 6 3 5 7 4 9 2])
    (def square (make-square values)))
  (let [square (make-square values)]
    (if (and
          (= (set (sum-cols square))
             (set (sum-diagonals square))
             (set (sum-rows square)))
          (= 1
             (count (set (sum-cols square)))
             (count (set (sum-diagonals square)))
             (count (set (sum-rows square)))))
      true
      false
      )
    ))

(defn magic-square [values]
  (make-square
    (first
      (filter
        magic-square?
        (combo/permutations values))))
  )