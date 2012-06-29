name := "tezcatlipoca"

organization := "com.geishatokyo"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

crossScalaVersions := Seq("2.9.1","2.9.1-1")

resolvers += Resolver.mavenLocal

publishTo := Some(Resolver.file("localMaven",Path.userHome / ".m2" / "repository"))

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.7" % "test"
)

libraryDependencies <+= (scalaVersion) { v => { v match{
      case "2.10" => "org.specs2" %% "specs2" % "1.11" % "test"
      case "2.9.1-1" => "org.specs2" %% "specs2" % "1.11" % "test"
      case "2.9.1" => "org.specs2" %% "specs2" % "1.11" % "test"
      case "2.9.0-1" => "org.specs2" %% "specs2" % "1.8.2" % "test"
      case "2.9.0" => "org.specs2" %% "specs2" % "1.7.1" % "test"
      case _ => "org.specs2" %% "specs2" % "1.11" % "test"
}}}