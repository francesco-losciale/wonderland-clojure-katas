(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

; [:fox :goose :corn :you] [:boat] []
; [:fox :goose ] [:boat] [:corn :you]
; [:fox :goose :you] [:boat] [:corn ]
; [:goose ] [:boat] [:corn :you :fox]
; [:goose :you] [:boat] [:corn :fox]
; [] [:boat] [:goose :you :corn :fox]

(defn remaining-items [[item you] all-items]
  (remove #(or (= % item) (= % you)) all-items))

(defn danger? [items]
  (or (= (set items) #{:goose :corn})
      (= (set items) #{:fox :goose})))

(defn river-crossing-plan []
  (let [[[left _ right]] start-pos
        items (filter #(not= % :you) left)
        options (for [item items]
                  (list item :you))]
    (remove
      #(danger?
         (remaining-items % left))
      options
      )
    ))

; given left list
; remove you and one more thing without leaving ...
; copy on right list
; add you to left list
; repeat until left list empty
