# PiApproximator

A small program that is able to calculate the estimation to the constant Pi using a variety of different techniques:
- Gregory-Leibniz
- Madhava-Leibniz
- Fabrice-Bellard
- Bailey-Borwein-Plouffe

Pi will be printed to stdout to 20 decimal places. The default approximation method is Gregory-Leibniz as per the requirements.

The program uses the Dagger dependency injection framework to initialise and create a factory containing each estimation technique. Akka and the actor model with simple round robin routing is used to perform the calculation in parallel. The model consists of a Controller which will create a number of Workers based on configuration parameters and a ResultListener to print out the result and cleanly shutdown the actor system. Currently each worker only process a single step (calculation) of the estimation and returns the result, there are better ways of handling this as discussed in the section Possible Improvements.

The Actor Model was chosen as it is a safe, lightweight, non-blocking, asynchronous and easy to use architectural pattern designed for concurrent operations. 

Using Dagger was purely out of interest as it is incredibly fast and lightweight although it lacks features of other DI frameworks like Guice and Spring DI. The use of Dagger DI could be more widespread than just for injecting estimations into a factory and full use of DI throughout the actor framework is possible. Due to time constraints, a sore back and sore eyes I'll stay away from this for now.

Handling of edge cases:
- When the number of workers is greater than the number of steps, the work will be divided across the workers as evenly as possible
- If the arguments passed to numberOfWorkers and numberOfSteps are not numbers, it will complain gracefully
- If the numberOfWorkers and numberOfSteps are less than or equal to zero, an InvalidParametersException will be thrown
- It is safe to assume that the Controller has valid input parameters, it will either be invoked from a test case or from the App entry point

## Installation Instructions

To install the program:

```
$ git clone https://github.com/brooms/PiApproximator.git
$ cd PiApproximator/
```

Import into IntelliJ IDEA or Eclipse. There is a pom.xml file in the root project directory that may be used as the project
model in either IDE.

## Build & Configuration Instructions

Prerequisites:
- Maven 3+
- JDK 1.8+

To build:

```
$ mvn clean install
```

The maven build will compile the sources, run checkstyle static analysis and execute both the unit and integration tests.

The checkstyle configuration is based on the google coding standards which can be found in the <b>checkstyle-configuration.xml</b> file in the root project directory.

An IntelliJ IDEA code style that adheres to the checkstyle configuration is provided in the <b>intellij-codestyle.xml</b> file.

The integration tests will run each approximation method to Pi against a default configuration using 1,000,000 steps and 10 workers and print out both the result and the duration of the computation.

## Run Demo

To run the demo application:

```
$ cp ./target/PiApproximator-1.0-SNAPSHOT-jar-with-dependencies.jar PiApproximatorApp.jar
$ java -jar PiApproximationApp.jar <numberOfSteps> <numberOfWorkers> <method>
```

Where:
- <i>numberOfSteps</i> is the number of steps the calculation should approximate Pi to
- <i>numberOfWorkers</i> is the number of concurrent workers performing calculations
- <i>method</i> (optional) is the approximation method to use, possible values are Madhava-Leibniz, Gregory-Leibniz, Fabrice-Bellard, Bailey-Borwein-Plouffe. The default approximation method being Gregory-Leibniz. 

E.g.
```
$ java -jar PiApproximationApp.jar 1000000 10
```
## Possible Improvements

There are a number of possible improvements that could be made from a performance and accuracy perspective:

1. Chunk up the work to each actor based on the number of steps and number of actors. Dealing with the edge case
where the number of steps is not nicely divisible by the number of actors. This has the potential to greatly speed
up the approximation process eliminating the overhead involved in marshalling & unmarshalling messages for tiny computation.
2. Somehow have the ability to dynamically detected the most optimum configuration for processing the workload. This will need to be handled at runtime and may involve adjusting the thread affinity.
3. The use of BigDecimal to improve the accuracy of the computations. I didn't want to have to deal with all the issues around boxing and unboxing of these and computations using BigDecimals.
4. Try using the inbuilt Java parallel streams instead of the actor model, this would simplify the implementation but it wouldn't be as interesting.
5. Plugging in code coverage and other metric generating tools (SonarQube, FindBugs, PMD, Technical Debt :-( ) into the build process.
6. More test cases to test edge cases and exception generating cases

## Update

Added work chunking based on the number of workers to improve performance. Handles the edge case where the amount of work is not cleanly divisible by the number of workers.

Details of how everything is divided up can be seen using debug log level. A possible way to improving the remainder situation is to spread it evenly across all workers, or spawn an additional worker to handle the remainder.
