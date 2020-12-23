(ns fox-goose-bag-of-corn.puzzle)

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



(defn river-crossing-plan []
  (remove danger? all-combinations))
