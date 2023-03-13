#!/bin/bash -x
curl --location 'localhost:8000/v1/getPersons'
echo ""

curl --location 'localhost:8000/v1/addPerson' \
--header 'Content-Type: application/json' \
--data '{
    "firstName" : "Vivek",
    "lastName" : "Gupta",
    "extra": "ex"
}'

echo ""

curl --location 'localhost:8000/v1/getPersons'
echo ""

curl --location --request GET 'localhost:8000/v1/getMovies' \
--header 'Content-Type: application/json' 
echo ""

curl --location 'localhost:8000/v1/addMovie' \
--header 'Content-Type: application/json' \
--data '{
        "id": "10",
        "title": "Movie10",
        "year": 20210,
        "actors": [
            {
                "firstName": "FNa10",
                "lastName": "LNa10"
            },
            {
                "firstName": "FNa200",
                "lastName": "LNa20"
            },
            {
                "firstName": "FNa30",
                "lastName": "LNa30"
            }
        ],
        "director": {
            "firstName": "FNd10",
            "lastName": "LNd10"
        }
    }'
echo ""

curl --location --request GET 'localhost:8000/v1/getMovies' \
--header 'Content-Type: application/json' 
echo ""
