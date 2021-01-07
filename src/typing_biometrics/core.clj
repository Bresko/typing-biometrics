(ns typing-biometrics.core
  (:gen-class)
  ;(:import java.awt.event.KeyEvent)
  (:import org.jnativehook.GlobalScreen)
  (:import org.jnativehook.keyboard.NativeKeyListener)
  (:require [clojure.java.io :as io]))


;; [[1 2][1 2][1]]
;; koje slovo se najcesce brise
;; koja je brzina kucanja 
;; keystroke pattern recognition
;; 
(defn write [q]
   (with-open [w (io/writer "C:\\Users\\ITsec\\Desktop\\temp.txt" :append true)]
      (.write w (str q))))

(defn write2 [action q millis username]
   (with-open [w (io/writer (str "C:\\Users\\ITsec\\Desktop\\" username ".txt") :append true)]
      (.write w (str action " " q " " millis "\n"))))


(def key-pressed 0)
(def key-released 0)
(def key)

(defn key-action [e action] 
  (let [k (.getRawCode e)]
    (str (print action " " k "  "))
    (println (System/currentTimeMillis))
    
    (println "old " key) ;;kada je key pressed onda se menja
    (println "current " k)
    (def key k)



    (if (= action "key-pressed")
      (def key-pressed (System/currentTimeMillis))
      (def key-released (System/currentTimeMillis)))

    (println "aa " key-pressed)
    (println "bb " key-released)
    (println "cc " (- key-pressed key-released))

    (if (= action "key-released")
      (write2 action k (- key-released key-pressed) "temp2"))
    ;;(write2 action k (System/currentTimeMillis) "temp2" )
    )
  )
    ;;(write2 action k (System/currentTimeMillis) "temp2" )))

(defn key-typed [e] ;;keylogger;;;;;
  (let [k (.getKeyChar e)]
    (write k)))

(defn myGlobalKeyListener []
  (reify
    NativeKeyListener    
    ;;(def currentTime(System/currentTimeMillis))
    (nativeKeyPressed [this event] (key-action event "key-pressed")) ;;button pressed and time
    (nativeKeyReleased [this event] (key-action event "key-released")) ;;button released and time
    (nativeKeyTyped [this event] (key-typed event)))) ;; standard keylogger


(defn mainMethod []
  (println "asdadasdasda")
      (GlobalScreen/registerNativeHook)
      (.addNativeKeyListener (GlobalScreen/getInstance) (myGlobalKeyListener))
    (println "keylogger started")
  
  ) 




(defn -main [& args]

  
  (mainMethod)

  
  )


