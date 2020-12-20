(ns alphabet-cipher.coder-test
  (:require [clojure.test :refer :all]
            [alphabet-cipher.coder :refer :all]))

(deftest test-resize-keyword
  (testing "can resize keyword to match message length"
    (is (= "sconessconessco"
           (resize-keyword "scones" "meetmebythetree")))))

(deftest test-get-index-of-char
  (testing "can get index of char in array"
    (is (= 3 (get-index \t "meetmebythetree")))
    (is (= 12 (get-index \m "abcdefghijklmnopqrstuvwxyz")))
    (is (= 18 (get-index \s "abcdefghijklmnopqrstuvwxyz")))
    ))

(deftest test-rotate
  (testing "can resize keyword to match message length"
    (is (= "bca" (rotate "abc" 1)))
    (is (= "cab" (rotate "abc" 2)))))


(deftest test-encode
  (testing "can encode a message with a secret keyword"
    (is (= "hmkbxebpxpmyllyrxiiqtoltfgzzv"
           (encode "vigilance" "meetmeontuesdayeveningatseven")))
    (is (= "egsgqwtahuiljgs"
           (encode "scones" "meetmebythetree")))))

(deftest test-decode
  (testing "can decode a message given an encoded message and a secret keyword"
    (is (= "meetmeontuesdayeveningatseven"
           (decode "vigilance" "hmkbxebpxpmyllyrxiiqtoltfgzzv")))
    (is (= "meetmebythetree"
           (decode "scones" "egsgqwtahuiljgs")))))

(deftest test-rep-string
  (testing "can extract sca from scascasc"
    (is (= "sca"
           (rep-string "scascascasc")))
    (is (= "scones"
           (rep-string "sconessconessconesscones")))
    (is (= "abcabcx"
           (rep-string "abcabcxabcabcxa")))))

(deftest test-decipher
  (testing "can extract the secret keyword given an encrypted message and the original message"
    (is (= "vigilance"
           (decipher "opkyfipmfmwcvqoklyhxywgeecpvhelzg" "thequickbrownfoxjumpsoveralazydog")))
    (is (= "scones"
           (decipher "hcqxqqtqljmlzhwiivgbsapaiwcenmyu" "packmyboxwithfivedozenliquorjugs")))
    (is (= "abcabcx"
           (decipher "hfnlphoontutufa" "hellofromrussia")))))
