package com.powerschool.apis.plainhttp4s

import cats.effect._
import com.powerschool.apis.plainhttp4s.PersonApis.Person
import fs2.Stream
import io.circe.Json
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, Request}

object MovieApis {

  case class Movie(id: String, title: String, year: Int, actors: List[Person], director: Person)

  //val movie1 = Movie("1", "Movie1", 2021, List(actor1, actor2, actor3), director1)
  //val movie2 = Movie("2", "Movie2", 2022, List(actor1, actor2, actor3), director2)
  val collectionOfMovies = scala.collection.mutable.Set[Movie]()

  def getMoviesList(req: Request[IO]) = {
    Stream.eval(Concurrent[IO].delay(
      Json.fromValues(collectionOfMovies.map(_.asJson))
    ))
  }

  def addMovie(req: Request[IO]): IO[Json] = {
    for (r <- req.as[String]) yield {
      val newMovie = decode[Movie](r)
      newMovie match {
        case Left(failure) => Json.fromString("Invalid json")
        case Right(movie) => {
          collectionOfMovies.add(movie)
          Json.fromString("Successfully added")
        }
      }
    }
  }

  def movieService = HttpRoutes.of[IO] {
    case req@GET -> Root / "v1" / "getMovies" => {
      Ok(getMoviesList(req))
    }

    case req@POST -> Root / "v1" / "addMovie" => {
      Ok(addMovie(req))
    }
  }
}
