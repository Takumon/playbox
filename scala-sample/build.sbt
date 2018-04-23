import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.5",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
      scalaTest % Test ,
      "org.specs2" %% "specs2-core" % "3.9.5" % "test"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos")
  )
