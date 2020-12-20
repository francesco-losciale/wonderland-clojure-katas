(ns wonderland-number.finder)

(defn has-same-digits? [n1 n2]
  (= (set (str n1)) (set (str n2))))

(defn is-magic? [n]
  (every? #(has-same-digits? n (* n %)) (range 2 7)))

(defn wonderland-number []
  (first (filter is-magic? (range 100000 999999)))
  )
