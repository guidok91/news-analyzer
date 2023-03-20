
lazy val root = project
  .in(file("."))
  .settings(
    name := "twitter-api-demo",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "3.2.2",

    scalafmtConfig := new File("conf/scalafmt.conf"),

    libraryDependencies += "com.typesafe" % "config" % "1.4.2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,
    libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC7",
    libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "4.5.2" artifacts(Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp")),
  )
