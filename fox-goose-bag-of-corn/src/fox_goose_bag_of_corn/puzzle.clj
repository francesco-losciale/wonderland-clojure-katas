(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :refer :all]))

(def start-pos [#{:fox :goose :corn :you} #{:boat} #{}])
(def end-pos [#{} #{:boat} #{:you :fox :goose :corn}])
;
;(defn danger? [items]
;  (and (not (some #(= % :you) items))
;       (or (and
;             (some #(= % :goose) items)
;             (some #(= % :corn) items))
;           (and
;             (some #(= % :fox) items)
;             (some #(= % :goose) items))
;           )))
;;
;;(defn off-you-and-item [list item]
;;  (disj list item :you))
;;
;;(defn on-you-and-item [list item]
;;  (conj list item :you))
;;
;;(defn off-you [list]
;;  (disj list :you))
;;(defn what-to-move-from-left-to-right [configuration]
;;  (let [[left-bank boat right-bank] configuration
;;        off-left-bank (partial off-you-and-item left-bank)
;;        on-right-bank (partial on-you-and-item right-bank)]
;;    (if (and
;;          (contains? left-bank :fox)
;;          (not (danger? (off-left-bank :fox)))
;;          (not (danger? (off-you (on-right-bank :fox))))
;;          )
;;      :fox
;;      (if (and
;;            (contains? left-bank :goose)
;;            (not (danger? (off-left-bank :goose)))
;;            (not (danger? (off-you (on-right-bank :goose))))
;;            )
;;        :goose
;;        (if (and
;;              (contains? left-bank :corn)
;;              (not (danger? (off-left-bank :corn)))
;;              (not (danger? (off-you (on-right-bank :corn)))))
;;          :corn
;;          (first (disj left-bank :you))                     ; pick one randome and bring it back
;;          ))))
;;  )
;;
;;(defn what-to-move-from-right-to-left [configuration]
;;  (let [[left-bank boat right-bank] configuration
;;        off-right-bank (partial off-you-and-item right-bank)
;;        on-left-bank (partial on-you-and-item left-bank)]
;;    (if (or
;;          (and (contains? right-bank :corn) (contains? right-bank :goose))
;;          (and (contains? right-bank :fox) (contains? right-bank :goose)))
;;      :goose
;;      (if (and (contains? right-bank :fox) (contains? right-bank :goose))
;;        :fox
;;        (if (danger? (off-right-bank :you))
;;          (first (disj right-bank :you))
;;          :nothing))
;;      ))
;;  )
;;
(defn move-right [configuration item]
  (comment
    (def configuration [#{:fox} #{:you :boat :corn} #{:goose}]))
  (let [[left-bank boat right-bank] configuration]
    [
     [(disj left-bank item :you) (conj boat item :you) right-bank]
     [(disj left-bank item :you) boat (conj right-bank item :you)]
     ;[(disj left-bank item :you) (conj boat :you) (conj right-bank item)]
     ;[(disj left-bank item) boat (conj right-bank item)]
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
;
;
;(def all-combinations
;  '(start-pos
;     (move-right start-pos (what-to-move-from-left-to-right start-pos))
;     (move-left (move-right start-pos (what-to-move-from-left-to-right start-pos)) (what-to-move-from-right-to-left (move-right start-pos (what-to-move-from-left-to-right start-pos))))
;     ;[#{:fox :corn} #{:you :boat} #{:goose}]
;     ;[#{:you :fox :corn} #{:boat} #{:goose}]
;     [#{:fox} #{:you :boat :corn} #{:goose}]
;     [#{:fox} #{:boat} #{:you :goose :corn}]
;     [#{:fox} #{:you :boat :goose} #{:corn}]
;     [#{:you :fox :goose} #{:boat} #{:corn}]
;     [#{:goose} #{:you :fox :boat} #{:corn}]
;     [#{:goose} #{:boat} #{:you :fox :corn}]
;     [#{:goose} #{:you :boat} #{:fox :corn}]
;     [#{:you :goose} #{:boat} #{:fox :corn}]
;     [#{} #{:you :boat :goose} #{:fox :corn}]
;     [#{} #{:boat} #{:you :fox :goose :corn}]))

(defn right-bank-items [[_ _ right-bank]]
  (disj right-bank :you))

(defn left-bank-items [[left-bank _ _]]
  (disj left-bank :you))

(defn invalid? [[left-bank _ right-bank]]
  (letfn [(danger? [items]
            (and
              ;(not (some #(= % :you) items))
              (or (and
                    (some #(= % :goose) items)
                    (some #(= % :corn) items))
                  (and
                    (some #(= % :fox) items)
                    (some #(= % :goose) items))
                  )))]
    (true? (or (danger? left-bank) (danger? right-bank))))
  )

(defn visited? [configuration path]
  (true? (> (count (filter #(= % configuration) path)) 1)))

(defn final? [configuration]
  (= configuration end-pos))

(defn calc-path [configuration path]
  (comment
    (def configuration [#{:you :fox :corn} #{:boat} #{:goose}])
    (def i :fox)
    (def path [[#{:fox :corn} #{:you :boat :goose} #{}] [#{:fox :corn} #{:boat} #{:you :goose}] [#{:fox :corn} #{:you :boat} #{:goose}] [#{:you :fox :corn} #{:boat} #{:goose}]]))
  (println configuration)
  (cond
    (invalid? configuration)
    (for [i (right-bank-items configuration)]
      (calc-path
        (last (move-left configuration i))
        (concat path (move-left configuration i))))
    (visited? configuration path)
    []
    (true? (some final? path))
    path
    :else
    (for [i (left-bank-items configuration)]
      (concat
        (calc-path
          (last (move-right configuration i))
          (concat path (move-right configuration i)))))
    )
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def start-pos [#{:goose :fox :corn :you} #{}])
(def end-pos [#{} #{:goose :fox :corn :you}])

(defn final? [configuration]
  (= configuration end-pos))

(defn visited? [configuration path]
  (println "config " configuration " and path " path)
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

(defn left-bank [[left-bank _]]
  (disj left-bank :you))

(defn right-bank [[_ right-bank]]
  (disj right-bank :you))

(defn you-on-right-bank? [[_ right-bank]]
  (contains? right-bank :you))

(defn calc-path [configuration path]
  (comment
    (def configuration start-pos)
    (def left-bank (first configuration)))
  ;(println path)
  (let [[lb _] configuration]
    (if (empty? lb)
      path
      (cond
        (visited? configuration path)
        (do
          ;(println "already visited " configuration)
          [])
        (and (you-on-right-bank? configuration)
             (not (invalid? (move-left configuration :you))))
        (do
          ;(println "next of " configuration " is " (move-left configuration :you))
          (calc-path
            (move-left configuration :you)
            (conj path (move-left configuration :you))))
        (and (you-on-right-bank? configuration)
             (invalid? (move-left configuration :you)))
        (do
          ;(println "stuck at " configuration)
          (remove empty?
                  (for [i (right-bank configuration) :when (not (invalid? (move-left configuration i)))]
                    (do
                      ;(println "next of " configuration " is " (move-left configuration i))
                      (calc-path
                        (move-left configuration i)
                        (conj path (move-left configuration i)))))))
        :else
        (do
          (remove empty?
                  (for [i (left-bank configuration) :when (not (invalid? (move-right configuration i)))]
                    (do
                      ;(println "next of " configuration " is " (move-right configuration i))
                      (calc-path
                        (move-right configuration i)
                        (conj path (move-right configuration i)))))))
        )
      ))
  )

; TODO substitute for with something else
;
;(defn calc-path [configuration path]
;  (comment
;    (def configuration [#{:fox} #{:you :goose :corn}]))
;  (let [[left-bank _] configuration]
;    (if (empty? left-bank)
;      path
;      ))
;  (cond
;    (and (you-on-right-bank? configuration)
;         (invalid? (move-left configuration :you)))
;    (do
;      (println "invalid " configuration " changed to " (move-left configuration :goose))
;      (calc-path
;        (move-left configuration :goose)
;        (conj path (move-left configuration :goose))))
;    (you-on-right-bank? configuration)
;    (calc-path
;      (move-left configuration :you)
;      (conj path (move-left configuration :you)))
;    (visited? configuration path)
;    (do
;      (println "visited already " configuration)
;      [])
;    (true? (some final? path))
;    (do
;      (println "final path " path)
;      path)
;    :else
;    (do
;      (for [i (left-bank configuration)]
;        (do
;          (println "next of " configuration " is " (move-right configuration i))
;          (calc-path
;            (move-right configuration i)
;            (conj path (move-right configuration i))))))
;    ))



(defn river-crossing-plan []
  (first (first (first (first (first (calc-path start-pos [start-pos])))))))