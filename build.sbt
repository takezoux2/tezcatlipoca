name := "tezcatlipoca"

organization := "com.geishatokyo"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

resolvers += Resolver.mavenLocal

publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.8.2" % "test",
  "junit" % "junit" % "4.7" % "test"
)