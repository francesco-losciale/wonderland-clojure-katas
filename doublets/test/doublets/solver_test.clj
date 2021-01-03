(ns doublets.solver-test
  (:require [clojure.test :refer :all]
            [doublets.solver :refer :all]))

(deftest helper-test
  (testing "true when two words are linked"
    (is (linked? "head" "heal"))
    (is (linked? "heal" "head"))
    (is (linked? "book" "look"))
    (is (linked? "look" "lock"))
    (is (not (linked? "teal" "tail")))
    (is (not (linked? "cheat" "cheep")))
    (is (not (linked? "book" "lock")))
    ))

(deftest solver-test
  (testing "with word links found"
    (is (= ["head" "heal" "teal" "tell" "tall" "tail"]
           (doublets "head" "tail")))

    (is (= ["door" "boor" "book" "look" "lock"]
           (doublets "door" "lock")))

    (is (= ["bank" "bonk" "book" "look" "loon" "loan"]
           (doublets "bank" "loan")))

    (is (= ["wheat" "cheat" "cheap" "cheep" "creep" "creed" "breed" "bread"]
           (doublets "wheat" "bread"))))

  (testing "with no word links found"
    (is (= []
           (doublets "ye" "freezer")))))
