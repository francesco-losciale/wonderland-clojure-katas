(ns fox-goose-bag-of-corn.puzzle-test
  (:require [clojure.test :refer :all]
            [fox-goose-bag-of-corn.puzzle :refer :all]
            [clojure.set]))

(defn validate-move [step1 step2]
  (testing "only you and another thing can move"
    (let [diff1 (clojure.set/difference step1 step2)
          diff2 (clojure.set/difference step2 step1)
          diffs (concat diff1 diff2)
          diff-num (count diffs)]
      (is (> 3 diff-num))
      (when (pos? diff-num)
        (is (contains? (set diffs) :you)))
      step2)))

(deftest test-river-crossing-plan
  (let [crossing-plan (map (partial map set) (river-crossing-plan))]
    (testing "you begin with the fox, goose and corn on one side of the river"
      (is (= [#{:you :fox :goose :corn} #{:boat} #{}]
             (first crossing-plan))))
    (testing "you end with the fox, goose and corn on one side of the river"
      (is (= [#{} #{:boat} #{:you :fox :goose :corn}]
             (last crossing-plan))))
    (testing "things are safe"
      (let [left-bank (map first crossing-plan)
            right-bank (map last crossing-plan)]
        (testing "the fox and the goose should never be left alone together"
          (is (empty?
                (filter #(= % #{:fox :goose}) (concat left-bank right-bank)))))
        (testing "the goose and the corn should never be left alone together"
          (is (empty?
                (filter #(= % #{:goose :corn}) (concat left-bank right-bank)))))))
    (testing "The boat can carry only you plus one other"
      (let [boat-positions (map second crossing-plan)]
        (is (empty?
              (filter #(> (count %) 3) boat-positions)))))
    (testing "moves are valid"
      (let [left-moves (map first crossing-plan)
            middle-moves (map second crossing-plan)
            right-moves (map last crossing-plan)]
        (reduce validate-move left-moves)
        (reduce validate-move middle-moves)
        (reduce validate-move right-moves)))))
;
;(deftest test-danger?
;  (let [goose-with-fox-danger #{:goose :fox}
;        goose-with-corn-danger #{:goose :corn}]
;    (is (danger? goose-with-corn-danger))
;    (is (danger? goose-with-fox-danger))
;    (is (not (danger? (conj goose-with-corn-danger :you))))
;    (is (not (danger? (conj goose-with-fox-danger :you))))
;    ))
;
;(deftest test-find-what-to-move
;  (is (= :goose (what-to-move-from-left-to-right [#{:you :fox :goose :corn} #{:boat} #{}])))
;  (is (= :goose (what-to-move-from-left-to-right [#{:you :goose} #{:boat} #{:corn :fox}])))
;  (is (= :corn (what-to-move-from-left-to-right [#{:you :goose :corn} #{:boat} #{:fox}])))
;  (is (= :fox (what-to-move-from-left-to-right [#{:you :goose :fox} #{:boat} #{:corn}])))
;  (is (= :fox (what-to-move-from-left-to-right [#{:you :corn :fox} #{:boat} #{:goose}])))
;  (is (= :goose (what-to-move-from-left-to-right [#{:you :goose} #{:boat} #{:fox :corn}])))
;  (is (contains? #{:goose :corn :fox} (what-to-move-from-left-to-right [#{:you :corn :fox} #{:boat} #{:goose}])))
;
;  (is (= :nothing (what-to-move-from-right-to-left [#{:you :fox :goose :corn} #{:boat} #{}])))
;  (is (= :nothing (what-to-move-from-right-to-left [#{:goose} #{:boat} #{:you :corn :fox}])))
;  (is (contains? #{:corn :goose} (what-to-move-from-right-to-left [#{:fox} #{:boat} #{:you :corn :goose}])))
;  (is (contains? #{:fox :goose} (what-to-move-from-right-to-left [#{:corn} #{:boat} #{:you :fox :goose}])))
;  )
;
;(deftest test-move-something
;  (is (= [[#{:goose :corn} #{:boat :you :fox} #{}]
;          [#{:goose :corn} #{:boat} #{:you :fox}]]
;         (move-right [#{:you :fox :goose :corn} #{:boat} #{}] :fox)))
;  (is (= [[#{:fox} #{:boat :you :corn} #{:goose}]
;          [#{:fox} #{:boat} #{:you :corn :goose}]]
;         (move-right [#{:you :fox :corn} #{:boat} #{:goose}] :corn)))
;  (is (= [[#{:corn} #{:boat :you :fox} #{:goose}]
;          [#{:corn :you :fox} #{:boat} #{:goose}]]
;         (move-left [#{:corn} #{:boat} #{:you :fox :goose}] :fox)))
;  (is (= [[#{:corn} #{:boat :you} #{:goose :fox}]
;          [#{:corn :you} #{:boat} #{:goose :fox}]]
;         (move-left [#{:corn} #{:boat} #{:you :fox :goose}] :nothing))))
