ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "toilet-near-me",
    idePackagePrefix := Some("io.andrelucas")
  )

lazy val logbackVersion = "1.5.6"
lazy val scalaLoggingVersion = "3.9.5"
lazy val PekkoVersion = "1.0.2"

lazy val scalaTestVersion = "3.2.18"
lazy val scalaMockitoVersion = "3.2.18.0"
lazy val slickVersion = "3.5.1"
lazy val pgVersion = "42.7.3"

lazy val slick = Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.typesafe.slick" %% "slick-testkit" % slickVersion
)


libraryDependencies += "ch.qos.logback"  %  "logback-classic"  % logbackVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
libraryDependencies += "org.apache.pekko" %% "pekko-actor-typed" % PekkoVersion

libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % Test
libraryDependencies += "org.scalatestplus" %% "mockito-5-10" % scalaMockitoVersion % Test
libraryDependencies += "org.apache.pekko" %% "pekko-actor-testkit-typed" % PekkoVersion % Test
libraryDependencies += "org.awaitility" % "awaitility-scala" % "4.2.0" % Test


libraryDependencies ++= slick
libraryDependencies += "org.postgresql" % "postgresql" % pgVersion
