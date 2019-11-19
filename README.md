# How to build the project

This project uses Gradle for project build and dependency management

## Class Diagram



## To build the project archive (jar)

```bash
$ git clone https://github.com/sujaybhowmick/me-assignment.git
$ gradlew clean jar
```

## To run the program (jar)

```bash
$ java -jar build/libs/me-assignment-1.0.jar ACC334455 "20/10/2018 12:00:00" "20/10/2018 19:00:00" < data.csv
Relative balance for the period is: -$25.00
Number of transactions included is: 1
```

## To run the Unit tests

```bash
$ gradlew test
```

