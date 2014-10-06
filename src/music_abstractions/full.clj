(ns music-abstractions.full
  (:use overtone.live))

; S.1 Waves
; ---------

; Sine waves and composite waves

(demo 3 (pan2 (sin-osc 440)))

(demo 3 (pan2 (saw 440)))

(demo 3 (pan2 (lf-tri:ar 440)))

(demo 3 (pan2 (lf-pulse:ar 440)))


; S.2 Instruments
; ---------------

; Composite waves + timber + inharmonics

; http://en.wikipedia.org/wiki/Harmonic
; http://en.wikipedia.org/wiki/Timbre
; http://en.wikipedia.org/wiki/Inharmonicity

; http://www.themeandvariations.org/Images/other/fourier_flute_violin.png

(def f-freq 440)
(demo 3 (pan2 (sin-osc f-freq)))
(demo 3 (pan2 (* 0.232 (+ (sin-osc f-freq)
                          (* (sin-osc (* f-freq 2)) 0.6)
                          (* (sin-osc (* f-freq 3)) 0.6)
                          (* (sin-osc (* f-freq 4)) 0.7)
                          (* (sin-osc (* f-freq 5)) 0.5)
                          (* (sin-osc (* f-freq 6)) 0.2)
                          (* (sin-osc (* f-freq 7)) 0.5)
                          (* (sin-osc (* f-freq 8)) 0.2)
))))

(demo 3 (pan 2 (map sin-osc)))

(demo 3 (pan2 (* 0.3984(+ (sin-osc f-freq)
                          (* (sin-osc (* f-freq 2)) 1.0)
                          (* (sin-osc (* f-freq 3)) 0.1)
                          (* (sin-osc (* f-freq 4)) 0.2)
                          (* (sin-osc (* f-freq 5)) 0.15)
                          (* (sin-osc (* f-freq 6)) 0.03)
                          (* (sin-osc (* f-freq 7)) 0.02)
                          (* (sin-osc (* f-freq 8)) 0.01)
))))

(demo 3 
      (+ (map 
         (fn [harm weight] (* (sin-osc (* f-freq harm)) weight)) 
         [1 2 3 4 5] 
         [1 0.5 0.2 0.4 0.1])))

(defn weighted-harmonic-osc [fundamental harmonic weight]
  (with-overloaded-ugens
    (pan2 (* (sin-osc (* fundamental harmonic)) weight))))

(demo 3 (weighted-harmonic-osc 220 1 0.5))

(defn instrument [fundamental harmonics weights]
  (let [total-weight (/ 1 (sum weights))
        osc (partial weighted-harmonic-osc fundamental)]
    (with-overloaded-ugens
      (pan2 (* total-weight (sum (map osc harmonics weights)))))))

(demo 3 (instrument 220 [1 2 3 4] [1 1 0.5 0.2]))

(definst five-ovr [freq 440 dur 3]
  (let [one   (sin-osc freq)
        two   (sin-osc (* freq 2))
        three (sin-osc (* freq 3))
        four  (sin-osc (* freq 4))
        five  (sin-osc (* freq 5))
        env (env-gen (lin 0.1 dur 0.1))]
    (* (+ one two three four five) env)))

(five-ovr 440)
(demo 3 (pan2 (sin-osc 440)))

