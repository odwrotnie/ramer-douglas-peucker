import Dependencies._

ThisBuild / organization := "app.instap"
ThisBuild / scalaVersion := "2.13.8"

lazy val ramerDouglasPeucker =
  project
    .in(file("."))
    .settings(name := "ramer–douglas–peucker")
    .settings(commonSettings)
    .settings(dependencies)

lazy val commonSettings =
  compilerPlugins ++ commonScalacOptions ++ Seq(
    update / evictionWarningOptions := EvictionWarningOptions.empty
  )

lazy val compilerPlugins = Seq(
  addCompilerPlugin(com.olegpy.`better-monadic-for`),
  addCompilerPlugin(org.augustjune.`context-applied`),
  addCompilerPlugin(org.typelevel.`kind-projector`),
)

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions := {
    (Compile / console / scalacOptions)
      .value
      .filterNot(_.contains("wartremover"))
      .filterNot(Scalac.Lint.toSet)
      .filterNot(Scalac.FatalWarnings.toSet) :+ "-Wconf:any:silent"
  },
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    // main dependencies
  ),
  libraryDependencies ++= Seq(
    com.github.alexarchambault.`scalacheck-shapeless_1.15`,
    org.scalacheck.scalacheck,
    org.scalatest.scalatest,
    org.scalatestplus.`scalacheck-1-15`,
    org.typelevel.`discipline-scalatest`,
    // "org.plotly-scala" %% "plotly-core" % "0.8.2",
    // "org.plotly-scala" %% "plotly-render" % "0.8.2",
    // "org.plotly-scala" %% "plotly-almond" % "0.8.2",
  ).map(_ % Test),
)
