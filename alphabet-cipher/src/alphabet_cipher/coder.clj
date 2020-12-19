(ns alphabet-cipher.coder)

(defn resize-keyword [keyword message]
  (apply str (take (count message) (cycle keyword))))

(defn encode [keyword message]
  "encodeme")

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

