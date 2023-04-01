
lazy val root = project
  .in(file("."))
  .settings(
    name := "twitter-api-demo",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "3.2.2",

    scalafmtConfig := new File("src/main/resources/scalafmt.conf"),

    resolvers += Classpaths.typesafeReleases,
    resolvers += "confluent" at "https://packages.confluent.io/maven/",

    libraryDependencies += "com.typesafe" % "config" % "1.4.2",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.3.5",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,

    libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0",
    libraryDependencies += "org.json4s" %% "json4s-jackson" % "4.0.6",

    libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "4.5.2" artifacts(Artifact("stanford-corenlp", "models"), Artifact("stanford-corenlp")),

    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.4.0",
    libraryDependencies += "io.confluent" % "kafka-avro-serializer" % "7.3.2",
    libraryDependencies += "org.apache.avro" % "avro" % "1.11.1",
  )
