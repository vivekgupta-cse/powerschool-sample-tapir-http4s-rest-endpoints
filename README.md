# sample-https-rest-endpoints

**To run the server, run ApiServerWithTapir**
**To change the serverUrl or serverPort, change it in src/main/resources/app.conf**

We maintain a very simplified database of movies, that we store in memory as Sets of case classes.
These are the case classes and corresponding sets:

case class Person(firstName: String, lastName: String)
val collectionOfPersons = scala.collection.mutable.Set\[Person\]()

case class Movie(id: String, title: String, year: Int, actors: List[Person], director: Person)
val collectionOfMovies = scala.collection.mutable.Set\[Movie\]()

Initially the sets are empty, and we can populate the data by using our Rest endpoints.

We have 4 APIs to add new data to these sets and return the complete set:
POST <Root>/v1/addPerson
example:- localhost:8000/v1/addPerson

GET <Root>/v1/getPersons
example:- localhost:8000/v1/getPersons

POST <Root>/v1/addMovie
example:- localhost:8000/v1/addMovie

GET <Root>/v1/getMovies
example:- localhost:8000/v1/getMovies

For the example test cases, please refer to the shell script "testApis.sh" in the test directory.
(Due to time constraints, shell script has been used instead of scalatest.)

Swagger UI available at:
http://<serverUrl>:<serverPort>/docs/
Example:- localhost:8000/docs/
