# Akka HTTP and Backpressure Presentation

## Building the presentation

This presentation is built with tut and reveal.js. The easiest way to work on it
is to run both of the following commands in seperate shell windows:

- `sbt ~tut`
- `npm install`
- `npm start`

This will update the presentation pages in `slides/`, and serve the updated
pages at `http://localhost:8001/` with live reload enable for easy viewing.

## Running the Demo

To start the demo of spray.io and akka http endpoints, run:

- `sbt ~reStart`

To access the blocking spray.io endpoint:

- `curl http://127.0.0.1:8080/blocking/1000`

And for the streaming Akka HTTP endpoint:

- `curl http://127.0.0.1:8090/streaming/1000`
