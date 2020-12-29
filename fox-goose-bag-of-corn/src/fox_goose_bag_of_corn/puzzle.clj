(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :refer :all]))

(def start-pos [#{:goose :fox :corn :you} #{}])
(def end-pos [#{} #{:goose :fox :corn :you}])

(defn moved-all-items? [[l _]]
  (empty? l))

(defn visited-already? [configuration path]
  (true? (> (count (filter #(= % configuration) path)) 1)))

(defn move-left [configuration item]
  (let [[left-bank right-bank] configuration]
    [(conj left-bank item :you) (disj right-bank item :you)]))

(defn move-right [configuration item]
  (let [[left-bank right-bank] configuration]
    [(disj left-bank item :you) (conj right-bank item :you)]))

(defn invalid? [[left-bank right-bank]]
  (letfn [(danger? [items]
            (and
              (not= [left-bank right-bank] start-pos)
              (not= [left-bank right-bank] end-pos)
              (not (contains? items :you))
              (or (and
                    (some #(= % :goose) items)
                    (some #(= % :corn) items))
                  (and
                    (some #(= % :fox) items)
                    (some #(= % :goose) items))
                  )))]
    (true? (or (danger? left-bank) (danger? right-bank))))
  )

(defn not-invalid? [configuration]
  (not (invalid? configuration)))

(defn left-bank [[left-bank _]]
  (disj left-bank :you))

(defn right-bank [[_ right-bank]]
  (disj right-bank :you))

(defn you-on-right-bank? [[_ right-bank]]
  (contains? right-bank :you))

(defn right-bank-without-you [configuration]
  (move-left configuration :you))

(defn calc-path
  "Given a configuration such as [[:you :goose :fox :corn] []],
  examine the graph of all the possible children configurations
  [[:fox :corn] [:you :goose]]
  [[:goose :corn] [:you :fox]]
  [[:goose :fox ] [:you :corn]]
  Apply recursively until all the items are moved on the right-bank
  safely, never leaving :goose and :fox or :goose and :corn alone on
  a river bank.
  Return the entire path when done.
  "
  [configuration path]
  (if (moved-all-items? configuration)
    path
    (cond
      ; return an empty path to avoid infinite loops
      (visited-already? configuration path)
      []
      ; when :you on the right-bank, move only if there's no harm
      ; for goose or corn
      (and (you-on-right-bank? configuration)
           (not-invalid? (right-bank-without-you configuration)))
      (let [next-move (move-left configuration :you)]
        (calc-path next-move (conj path next-move)))
      ; when :you would leave the right bank, and it would be dangerous
      ; for goose/corn, take something with you
      (and (you-on-right-bank? configuration)
           (invalid? (move-left configuration :you)))
      (remove empty?
              (for [i (right-bank configuration) :when (not-invalid? (move-left configuration i))]
                (let [next-move (move-left configuration i)]
                  (calc-path next-move (conj path next-move)))))
      :else
      ; create all the possible paths
      (remove empty?
              (for [i (left-bank configuration) :when (not-invalid? (move-right configuration i))]
                (let [next-move (move-right configuration i)]
                  (calc-path next-move (conj path next-move)))))
      )
    )
  )

(defn enrich-output-path
  "Translates
  [[[:you :goose :fox: :corn] []           ]
   [[     :goose :fox:]       [:corn :you ]]]
   into...
  [[[:you :goose :fox: :corn] [:boat]            []          ]
   [[ :goose :fox: ]          [:boat :corn :you] []          ]
   [[ :goose :fox:]       [:boat]            [:corn :you]]]
  This logic has been extracted to simplify calc-path.
  It is applied only after the path is calculated."
  [path]
  (let [[first third] (map #(let [[l r] %]
           [l #{:boat} r]) path)
        [l1 _ r1] first
        [l3 _ _] third]
    [first [(difference l1 (conj (difference l1 l3) :boat))
            (conj (difference l1 l3) :boat)
            r1] third]
    ))

(defn river-crossing-plan []
  (let [path (first (first (first (first (first (calc-path start-pos [start-pos]))))))]
    (mapcat enrich-output-path (partition 2 path))))