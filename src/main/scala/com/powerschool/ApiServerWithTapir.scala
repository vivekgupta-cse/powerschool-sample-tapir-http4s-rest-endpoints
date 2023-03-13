package com.powerschool

import cats.effect._
import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}
import com.powerschool.apis.tapir.MovieApisTapir.movieApiEndpoints
import com.powerschool.apis.tapir.PersonApisTapir.personApiEndpoints
import com.typesafe.config.{Config, ConfigFactory}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object ApiServerWithTapir extends IOApp {


  val docEndpoints: List[ServerEndpoint[Any, IO]] = SwaggerInterpreter()
    .fromServerEndpoints[IO](movieApiEndpoints ++ personApiEndpoints,
      "powerschool-assignment", "1.0.0")

  val allEndpoints: List[ServerEndpoint[Any, IO]] =
    movieApiEndpoints ++ personApiEndpoints ++ docEndpoints

  override def run(args: List[String]): IO[ExitCode] = {
    val routes = Http4sServerInterpreter[IO]().toRoutes(allEndpoints)

    val applicationConf: Config = ConfigFactory.load("app.conf")

    val serverPort = Port.fromInt(applicationConf.getInt("serverPort")).getOrElse(port"8080")
    val serverUrl = applicationConf.getString("serverUrl")

    EmberServerBuilder
      .default[IO]
      .withHost(Host.fromString("localhost").get)
      .withPort(serverPort)
      .withHttpApp(Router("/" -> routes).orNotFound)
      .build
      .use { server =>
        for {
          _ <- IO.println(s"Server started at ${serverUrl}:${serverPort}. Press ENTER key to exit.")
          _ <- IO.readLine
        } yield ()
      }
      .as(ExitCode.Success)
  }
}
