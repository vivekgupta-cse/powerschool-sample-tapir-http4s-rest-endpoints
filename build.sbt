name := "powerschool-coding-assignment"
version := "0.1"
scalaVersion := "2.13.10"

import Dependencies._

libraryDependencies ++= Seq(
  circe.core,
  circe.generic,
  circe.parser,
  http4s.core,
  http4s.circe,
  http4s.blazeServer,
  http4s.emberServer,
  cats.core,
  cats.effect,
  tapir.core,
  tapir.jsonCirce,
  tapir.tapirServer,
  tapir.swaggerUi,
  typesafe.config
)