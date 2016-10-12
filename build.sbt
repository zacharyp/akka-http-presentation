import sbt.Keys._

organization := "org.zachary.demo"
name := "akka-http-demo"
version := "1.0"

val scalaV = "2.11.8"
val akkaVersion = "2.4.11"
val sprayVersion = "1.3.2"

tutSettings
tutTargetDirectory := baseDirectory.value / "slides"

lazy val Akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion
)

lazy val akkaHttpDemo = project.in(file("."))
  .settings(
    scalaVersion := scalaV,
    mainClass in(Compile, run) := Some("org.zachary.demo.Main")
  )
  .settings(libraryDependencies ++=
    Akka ++ Seq(
      "io.spray" %% "spray-can" % sprayVersion,
      "io.spray" %%  "spray-json" % sprayVersion,
      "io.spray" %% "spray-routing" % sprayVersion
//      "org.json4s" %% "json4s-jackson" % "3.3.0",
//      "org.json4s" %% "json4s-native" % "3.3.0"
    )
  )
