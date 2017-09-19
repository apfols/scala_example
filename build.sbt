name := "scala_example"

version := "0.1"

scalaVersion := "2.12.3"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0-MF",
  "org.typelevel" %% "cats-free" % "1.0.0-MF",
  "com.github.mpilquist" %% "simulacrum" % "0.10.0"
)

resolvers += Resolver.sonatypeRepo("releases")

