ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "toilet-near-me",
    idePackagePrefix := Some("io.andrelucas")
  )


lazy val logbackVersion = "1.5.6"
lazy val scalaLoggingVersion = "3.9.5"
lazy val scalaTestVersion = "3.2.18"

libraryDependencies += "ch.qos.logback"  %  "logback-classic"  % logbackVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"