(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.set :as s]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(def words ["door"
            "boor"
            "book"
            "look"
            "lock"])

(defn doublets? [word1 word2]
  (comment
    (def word1 "look")
    (def word2 "lock")
    (def children (disj (set (filter #(doublets? word1 %) words)) word1))
    (def next-words (s/difference (set words) (conj children word1)))
    (def path [])
    )
  (and (= (count word1) (count word2))
       (and
         (<= 1 (count
              (s/union
                (s/difference (set word2) (set word1))
                (s/difference (set word1) (set word2)))) 2))))

(defn find-path [word1 word2 words path]
  (let [children (set (filter #(doublets? word1 %) words))
        next-words (s/difference (set words) (conj children word1))]
    (do
      (println " word1 " word1 " word2 " word2 " children " children " next words " next-words " path " path)
      (if (doublets? word1 word2)
        (conj path word2)
        (mapcat #(find-path % word2 next-words (conj path %)) children)))))

(defn doublets [word1 word2]
  (find-path word1 word2 words []))
