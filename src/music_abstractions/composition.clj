(ns overtone-play.composition
  (:use overtone.live))

(definst chime [freq 440 amp 0.2 dur 1]
  (let [over-2 (* freq 2)
        over-3 (* freq 3.1)
        over-4 (* freq 6.2)]
    (* (+ (* (sin-osc freq) 1)
          (* (sin-osc over-2) 0.7)
          (* (sin-osc over-3) 0.2)
          (* (sin-osc over-4) 0.1))
       0.5 amp
       (env-gen (lin 0 0.1 dur) :action FREE))))

(definst hi-hat [strum 0.1]
  (pan2 (* (hpf (white-noise) 9000)
           (env-gen (perc 0.0 strum :curve -8) :action FREE))))

(definst bass [freq 110 amp 0.8 dur 5]
  (let [fundamental-fq freq
        overtones [1 2 3 4 5 6 7 8]
        weights [1 0.1 0.8 0.7 0.6 0.7 0.4 0.2]
        fqs (map (partial * fundamental-fq) overtones)
        total-weight (* 0.2 amp)]
    (pan2 (* (sum (map * (map sin-osc fqs) weights))
             (env-gen (lin (* dur 2/5) (* dur 1/5) (* dur 2/5)) :action FREE)
             total-weight))))

(definst choir [freq 880 dur 7]
  (let [fund freq
        fifth (* fund 3/2)
        filter (/ fund 2)
        vibr_fq 5 vibr_mag 13
        trem_fq 1 trem_mag 0.1]
    (pan2 (* (lpf (+ (lf-tri (+ fund (* vibr_mag (sin-osc vibr_fq))))
                     (lf-tri (+ fifth (* vibr_mag (sin-osc vibr_fq)))))
                  filter)
             (+ 1 (* (sin-osc trem_fq) trem_mag))
             (env-gen (lin 0.2 0.2 (- dur 0.4)) :action FREE))
          (* 0.25 (sin-osc 0.5)))))

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

(defn play-pattern [cur-t sep-t seq sound]
  (at cur-t (when (first seq) (apply sound (first seq))))
  (let [new-t (+ cur-t sep-t)]
    (apply-by new-t #'play-pattern [new-t sep-t (rest seq) sound])))

(defn play-all [sep-t patterns]
  (let [t (+ (now) 200)]
    (doseq [[sound pattern] patterns]
      (play-pattern t sep-t pattern sound))))

(def ms-sep (/ 1 (/ (/ bpm 60) 1000)))
(play-all ms-sep {hi-hat (cycle hat-seq)
                  chime chime-seq
                  bass (cycle bass-seq)
                  choir choir-seq})

