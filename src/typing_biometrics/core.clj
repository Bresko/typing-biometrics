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
(defn write [q]
   (with-open [w (io/writer "C:\\Users\\ITsec\\Desktop\\temp.txt" :append true)]
      (.write w (str q))))

(defn write2 [action q millis username]
   (with-open [w (io/writer (str "C:\\Users\\ITsec\\Desktop\\" username ".txt") :append true)]
      (.write w (str action " " q " " millis "\n"))))

(defn key-action [e action] 
  (let [k (.getRawCode e)]
    (str (print action " " k "  "))
    (println (System/currentTimeMillis))
    (write2 action k (System/currentTimeMillis) "temp2" )
    )
  )

(defn key-typed [e] ;;keylogger
  (let [k (.getKeyChar e)]
    (write k)
    )
  )

(defn myGlobalKeyListener []
  (reify
    NativeKeyListener    
    (nativeKeyPressed [this event] (key-action event "key-pressed")) ;;button pressed and time
    (nativeKeyReleased [this event] (key-action event "key-released")) ;;button released and time
    (nativeKeyTyped [this event] (key-typed event)))) ;; standard keylogger

(defn -main [& args]
    (GlobalScreen/registerNativeHook)
    (.addNativeKeyListener (GlobalScreen/getInstance) (myGlobalKeyListener))
    (println "keylogger started")
)


