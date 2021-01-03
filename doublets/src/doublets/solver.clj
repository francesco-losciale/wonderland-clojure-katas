(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.set :as s]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn linked? [word1 word2]
  (and (= (count word1) (count word2))
       (= (count
            (filter true?
                    (map #(not= %1 %2) word1 word2))) 1)))

(defn find-path [word1 word2 words path]
  (let [children (set (filter #(linked? word1 %) words))
        next-words (s/difference (set words) (conj children word1))]
    (if (linked? word1 word2)
      (conj path word2)
      (mapcat #(find-path % word2 next-words (conj path %)) children))))

(defn doublets [word1 word2]
  (find-path word1 word2 words [word1]))
