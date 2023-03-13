package com.powerschool.apis.tapir

import cats.effect.IO
import com.powerschool.apis.tapir.PersonApisTapir.Person
import io.circe.generic.auto._
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.server.ServerEndpoint

object MovieApisTapir {
  case class Movie(id: String, title: String, year: Int, actors: List[Person], director: Person)

  val collectionOfMovies = scala.collection.mutable.Set[Movie]()

  val movieListing: PublicEndpoint[Unit, String, List[Movie], Any] = endpoint.get
    .in("v1" / "getMovies")
    .errorOut(stringBody.description("Something went wrong"))
    .out(jsonBody[List[Movie]])

  val movieListingServerEndpoint: ServerEndpoint[Any, IO] = movieListing.serverLogicSuccess(_ => IO.pure(collectionOfMovies.toList))

  val addMovieInList: PublicEndpoint[Movie, String, String, Any] = endpoint.post
    .in("v1" / "addMovie")
    .in(jsonBody[Movie])
    .errorOut(stringBody.description("Something went wrong"))
    .out(jsonBody[String])


  val addMovieEndpoint = addMovieInList.serverLogicSuccess(m => {
    m match {
      case Movie(i, t, y, a, d) => collectionOfMovies.add(Movie(i, t, y, a, d)); IO.pure("Success")
      case _ => IO.pure("Failure")
    }
  })

  val movieApiEndpoints: List[ServerEndpoint[Any, IO]] = List(movieListingServerEndpoint, addMovieEndpoint)
}
