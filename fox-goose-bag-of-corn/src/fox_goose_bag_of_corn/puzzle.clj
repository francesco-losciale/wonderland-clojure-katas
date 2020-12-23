(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :refer :all]))

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(def all-combinations
  '([#{:you :fox :goose :corn} #{:boat} #{}]
    [#{:fox :corn} #{:you :boat :goose} #{}]
    [#{:fox :corn} #{:boat} #{:you :goose}]
    [#{:fox :corn} #{:you :boat} #{:goose}]
    [#{:you :fox :corn} #{:boat} #{:goose}]
    [#{:fox} #{:you :boat :corn} #{:goose}]
    [#{:fox} #{:boat} #{:you :goose :corn}]
    [#{:fox} #{:you :boat :goose} #{:corn}]
    [#{:you :fox :goose} #{:boat} #{:corn}]
    [#{:goose} #{:you :fox :boat} #{:corn}]
    [#{:goose} #{:boat} #{:you :fox :corn}]
    [#{:goose} #{:you :boat} #{:fox :corn}]
    [#{:you :goose} #{:boat} #{:fox :corn}]
    [#{} #{:you :boat :goose} #{:fox :corn}]
    [#{} #{:boat} #{:you :fox :goose :corn}]))

(defn danger? [items]
  (and (not (some #(= % :you) items))
       (or (and
             (some #(= % :goose) items)
             (some #(= % :corn) items))
           (and
             (some #(= % :fox) items)
             (some #(= % :goose) items))
           )))

(defn off-you-and-item [list item]
  (disj list item :you))

(defn on-you-and-item [list item]
  (conj list item :you))

(defn off-you [list]
  (disj list :you))

(defn what-to-move-from-left-to-right [configuration]
  (let [[left-bank boat right-bank] configuration
        off-left-bank (partial off-you-and-item left-bank)
        on-right-bank (partial on-you-and-item right-bank)]
    (if (and
          (contains? left-bank :fox)
          (not (danger? (off-left-bank :fox)))
          (not (danger? (off-you (on-right-bank :fox))))
          )
      :fox
      (if (and
            (contains? left-bank :goose)
            (not (danger? (off-left-bank :goose)))
            (not (danger? (off-you (on-right-bank :goose))))
            )
        :goose
        (if (and
              (contains? left-bank :corn)
              (not (danger? (off-left-bank :corn)))
              (not (danger? (off-you (on-right-bank :corn)))))
          :corn
          (first (disj left-bank :you))                             ; pick one randome and bring it back
          ))))
  )

(defn river-crossing-plan []
  (remove danger? all-combinations))
