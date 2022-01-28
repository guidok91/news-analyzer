
lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-template",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "3.1.0",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
  )
