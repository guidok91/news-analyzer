
lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-example",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "3.1.0",

    libraryDependencies += "com.typesafe" % "config" % "1.4.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    libraryDependencies += "com.lihaoyi" %% "requests" % "0.7.0",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "1.5.0",
  )
