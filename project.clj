(defproject typing-biometrics "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [overtone "0.10.6"]
                 [org.clojars.pepijndevos/jnativehook "1.1.4"]]
  :main ^:skip-aot typing-biometrics.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
