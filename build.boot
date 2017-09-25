(def +dependencies+
  '[[org.clojure/tools.logging "0.4.0"]
    [org.clojure/data.json "0.2.6"]
    [org.clojure/data.zip "0.1.2"]
    [log4j/log4j "1.2.17"]
    [cheshire "5.6.3"]
    [org.clojure/data.codec "0.1.0"]
    [clj-http "3.7.0"]])

(set-env!
  :source-paths #{"src"}
  :dependencies +dependencies+)

(def +version+ "0.1.6-SNAPSHOT")
(def +jar+ "etaoin.jar")
(def +target+ "release-files")

(task-options!
  target {:dir #{+target+}}
  push {:repo "clojars"
        :gpg-sign true}
  pom {:project 'kovacnica/etaoin
       :version +version+
       :dependencies +dependencies+}
  jar {:manifest {"created-by" "Robert Gersak"}
       :file +jar+})

(deftask dev
  "Starts up development environment"
  []
  (comp
    (repl
      :init-ns 'playground.appium
      :port 4001)
    (target :dir #{"target"})))

(deftask build
  "Build dreamcatcher and install localy"
  []
  (comp (pom) (jar) (install)))

(deftask push-snapshot 
  "Pushes current snapshot to clojars"
  []
  (comp 
    (build)
    (push :repo "clojars" :ensure-release false :ensure-snapshot true :gpg-sign true)))
