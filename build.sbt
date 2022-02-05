
lazy val root = project
  .in(file("."))
  .settings(
    name := "twitter-api-demo",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "3.1.0",

    scalafmtConfig := new File("conf/.scalafmt.conf"),

    libraryDependencies += "com.typesafe" % "config" % "1.4.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    libraryDependencies += "com.lihaoyi" %% "requests" % "0.7.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
  )
