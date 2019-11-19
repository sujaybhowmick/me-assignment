# How to build the project

This project uses Gradle for project build and dependency management

## Technology Stack

1. Runtime - Java 8
2. Programming Language - Kotlin
3. Unit testing framework - JUnit5

## Class Diagram

![CD](https://raw.githubusercontent.com/sujaybhowmick/me-assignment/master/Main.png)

## How to build the project archive (jar)

```bash
$ git clone https://github.com/sujaybhowmick/me-assignment.git
$ gradlew clean jar
```

## How to run the program (jar)

```bash
$ java -jar build/libs/me-assignment-1.0.jar ACC334455 "20/10/2018 12:00:00" "20/10/2018 19:00:00" < data.csv
Relative balance for the period is: -$25.00
Number of transactions included is: 1
```

## How to run the Unit tests

```bash
$ gradlew test
```

## Contact Details

Email: sujaybhowmick@gmail.com

LinkedIn: [Sujay Bhowmick](https://www.linkedin.com/in/sujaybhowmick/)

