# Project tracker
> this is a small application for managing project build with spring boot & ReactJs and Postgres.


### Features

- Login.
- Create & update and delete project.
- Create and update budget.
- Assign budget to project.

# Run project
> Running application using docker :
> After cloning the project  open the terminal execute this commande :
````shell
cd sensor-interview-test/server/project-trakcer-api

./gradlew clean build

# and now the .jar file are generated we go the root folder of project (sensor-interview-test)

docker compose up

# And if all is good you will see this in your terminal 😃

[+] Running 3/3
 ✔ Container app_postgres         Started
 ✔ Container project-tracker-api  Started
 ✔ Container project-tracker-ui   Started

# You can access the app by browsing localhost:5173
