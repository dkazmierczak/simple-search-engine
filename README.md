## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Usage](#usage)

## General info
This project is a simple search engine implementing inverted index and TF-IDF sorting.

## Technologies
Project is created with:
* Java: 11
* Sring Boot: 2.76
* Maven: 3.6.1

## Setup
To run this project, install it locally using npm:

```
$ cd ../simple-search-engine
$ mvn install
$ mvn spring-boot::run
```

To stop running application press ctrl+c

## Usage
This application exposes two endpoints:

### API Endpoints
| HTTP Verbs | Endpoints | Action                              | Data                                     |
|------------|-----------|-------------------------------------|------------------------------------------|
| POST       | /document | To upload documents                 | Required List of Strings in request body | 
| GET        | /document | To query storage for particular ter | Reuired term as a request param          |