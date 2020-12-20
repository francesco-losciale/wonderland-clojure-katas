(ns wonderland-number.finder)

(defn has-same-digits? [n1 n2]
  (= (set (str n1)) (set (str n2))))

(defn is-magic? [n]
  (and
    (has-same-digits? n (* n 2))
    (has-same-digits? n (* n 3))
    (has-same-digits? n (* n 4))
    (has-same-digits? n (* n 5))
    (has-same-digits? n (* n 6))
    )
  )

(defn wonderland-number []
  (first (filter is-magic? (range 100000 999999)))
  )
