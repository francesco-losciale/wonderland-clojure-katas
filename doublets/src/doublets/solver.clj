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
    (def word1 "head")
    (def word2 "heal")
    (def word1 "boor")
    (def children (disj (set (filter #(doublets? word1 %) words)) word1))
    (def next-words (s/difference (set words) children word1))
    )
  (and (= (count word1) (count word2))
       (and
         (= (count
              (s/union
                (s/difference (set word2) (set word1))
                (s/difference (set word1) (set word2)))) 2))))

(defn find-path [word1 word2 words]
  (let [children (disj (set (filter #(doublets? word1 %) words)) word1)
        next-words (s/difference (set words) (conj children word1))]
    (do
      (println " word1 " word1 " word2 " word2 " children " children " next words " next-words)
      (if (not (empty? words))
        (if (contains? children word2)
          [word2]
          (for [w children]
            (concat (find-path w word2 next-words))))))))

(defn doublets [word1 word2]
  (find-path word1 word2 words))
