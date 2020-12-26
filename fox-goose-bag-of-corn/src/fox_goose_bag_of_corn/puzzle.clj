(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :refer :all]))

(def start-pos [#{:fox :goose :corn :you} #{:boat} #{}])
(def end-pos [#{} #{:boat} #{:you :fox :goose :corn}])

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
          (not (contains? right-bank :goose))
          (not (danger? (off-left-bank :fox)))
          (not (danger? (off-you (on-right-bank :fox))))
          )
      :fox
      (if (and
            (contains? left-bank :goose)
            (or
              (not (danger? (off-left-bank :goose)))
              (= (on-right-bank :goose) end-pos))
            (not (danger? (off-you (on-right-bank :goose))))
            )
        :goose
        (if (and
              (contains? left-bank :corn)
              (not (danger? (off-left-bank :corn)))
              (not (danger? (off-you (on-right-bank :corn)))))
          :corn
          ;(first (disj left-bank :you))                             ; pick one randome and bring it back
          :corn
          ))))
  )

(defn what-to-move-from-right-to-left [configuration]
  (let [[left-bank boat right-bank] configuration
        off-right-bank (partial off-you-and-item right-bank)
        on-left-bank (partial on-you-and-item left-bank)]
    (if (danger? (off-right-bank :you))
      (first (disj right-bank :you))
      :nothing)))

(defn move-right [configuration item]
  (let [[left-bank boat right-bank] configuration]
    [
     [(disj left-bank item :you) (conj boat item :you) right-bank]
     [(disj left-bank item :you) boat (conj right-bank item :you)]
     ]
    ))

(defn move-left [configuration item]
  (let [[left-bank boat right-bank] configuration]
    (if (= item :nothing)
     [[left-bank (conj boat :you) (disj right-bank :you)]
      [(conj left-bank :you) boat (disj right-bank :you)]]
     [[left-bank (conj boat item :you) (disj right-bank item :you)]
      [(conj left-bank item :you) boat (disj right-bank item :you)]])
    ))


(def all-combinations
  '(start-pos
     (move-right start-pos (what-to-move-from-left-to-right start-pos))
     (move-left (move-right start-pos (what-to-move-from-left-to-right start-pos)) (what-to-move-from-right-to-left (move-right start-pos (what-to-move-from-left-to-right start-pos))))
     ;[#{:fox :corn} #{:you :boat} #{:goose}]
     ;[#{:you :fox :corn} #{:boat} #{:goose}]
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

(defn river-crossing-plan []
  (loop [configuration start-pos
         result []]
    (if (not (some #(= % end-pos) result))                    ; (not= (last result) end-pos)
      (let [new-configuration (move-right configuration (what-to-move-from-left-to-right configuration))
            new-new-configuration (move-left (last new-configuration) (what-to-move-from-right-to-left (last new-configuration)))]
        (recur (last new-new-configuration) (concat new-configuration new-new-configuration)))
      result)
    ))
