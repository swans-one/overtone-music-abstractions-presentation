(ns music-abstractions.full
  (:use overtone.live))

;; (demo 5 (pan2 (+ (sin-osc 440) (sin-osc (* 440 2)))))

(definst chime [freq 440 dur 1]
  (let [fund freq
        over1 (* freq 2)
        over2 (* freq 3.1)
        over3 (* freq 6.2)]
    (* (pan2 (+ (* (sin-osc fund) 1)
                (* (sin-osc over1) 0.7)
                (* (sin-osc over2) 0.2)
                (* (sin-osc over3) 0.1)))
       (env-gen (lin 0 0.1 dur))
       0.5)))

;; (chime 440)

;; (stop)

;; (demo 4 (pan2 (white-noise)))

;; (demo 4 (pan2 (* (env-gen (lin 1 1 1)) (sin-osc 440))))

(definst hi-hat [strum 0.1]
  (pan2 (* (env-gen (perc 0 strum) :action FREE) 
           (hpf (white-noise) 9000))))

;; (hi-hat)

(definst bass [freq 110 amp 0.8 dur 5]
  (let [fundamental-fq freq
        overtones [1 2 3 4 5 6 7 8]
        weights [1 0.1 0.8 0.7 0.6 0.7 0.4 0.2]
        fqs (map (partial * fundamental-fq) overtones)
        total-weight (* 0.2 amp)]
    (pan2 (* (sum (map * (map sin-osc fqs) weights))
             (env-gen (lin (* dur 2/5) (* dur 1/5) (* dur 2/5)) :action FREE)
             total-weight))))

;; (bass (midi->hz 48) 2 7)

(definst choir [freq 880 dur 7]
  (let [fund freq
        fifth (* freq 3/2)
        vib_fq 5 vib_mag 13
        trem_fq 1 trem_mag 0.1]
    (pan2 (* (+ (lf-tri (+ fund (* vib_mag (sin-osc vib_fq))) 
                        (+ fifth (* vib_mag (sin-osc vib_fq)))))
             (env-gen (lin 0.2 0.2 dur))
             (+ 1 (* (sin-osc trem_fq) trem_mag))
             (* 0.25 (sin-osc 0.5))))))

;; (choir)

(defn play-pattern [cur-t sep-t seq sound]
  (at cur-t (when (first seq) (apply sound (first seq))))
  (let [new-t (+ cur-t sep-t)]
    (apply-by new-t #'play-pattern [new-t sep-t (rest seq) sound])))


(defn play-all [sep-t patterns]
  (let [t (+ (now) 200)]
    (doseq [[sound pattern] patterns]
      (play-pattern t sep-t pattern sound))))


(def bpm 200)
(def beat-dur (/ 60 bpm))

(def hat-seq [[] [0.5] nil nil nil [] nil [] [] nil nil]) ;; 5

(def bass-seq (concat [[:freq (midi->hz 48) :dur (* 12 beat-dur)]]
                      (repeat 6 nil)
                      [[:freq (midi->hz 55) :dur (* 12 beat-dur)]]
                      (repeat 5 nil)
                      [[:freq (midi->hz 52) :dur (* 12 beat-dur)]]
                      (repeat 3 nil))) ;; 17

(def chime-seq
  (repeatedly #(if-let [a-note (rand-nth [60 64 67 70 72 76 79 nil])]
                 (vector (midi->hz a-note) :dur (* 1.5 beat-dur))
                 nil)))

(def choir-seq (repeatedly #(if (< 0.90 (rand))
                              [(midi->hz (rand-nth [72 82 88]))
                               (rand-int 8)]
                              nil)))

(play-all 300 {hi-hat (cycle hat-seq)
               chime chime-seq
               bass (cycle bass-seq)
               choir choir-seq})
                      
(stop)
