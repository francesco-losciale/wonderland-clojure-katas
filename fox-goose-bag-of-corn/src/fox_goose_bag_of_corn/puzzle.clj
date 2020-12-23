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

;(defn remaining-items [[item you] all-items]
;  (remove #(or (= % item) (= % you)) all-items))

(defn fox-or-goose-danger? [items]
  (and (not (some #(= % :you) items))
       (or (and
             (some #(= % :goose) items)
             (some #(= % :corn) items))
           (and
             (some #(= % :fox) items)
             (some #(= % :goose) items))
           )))
(defn danger? [[left-bank _ right-bank]]
  (or
    (fox-or-goose-danger? left-bank)
    (fox-or-goose-danger? right-bank)))

(defn river-crossing-plan []
  (remove danger? all-combinations))

; given left list
; remove you and one more thing without leaving ...
; copy on right list
; add you to left list
; repeat until left list empty
