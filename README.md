# cinemaster
 A cinema manager project, provided with a MySQL Server persistance layer, a Spring Boot backend layer and an Angular frontend layer.

## How to run
To run the project you must have installed Docker and Docker Compose. After this step, just open a terminal in the folder of the project (the same level of the file `docker-compose.yml`) and run:
`docker-compose up --build`

Wait until the three layers are set up and then visit http://localhost:4200/home

## Application features
The application is a cinema manager. There are four types of user:
- `guest`: is a simple user that is not logged-in. He can view upcoming events and see if there are free seats for specific events.
- `user`: is a guest that is logged in. He starts with the same privileges of `guest`. Moreover, he can buy tickets, see a list of bought tickets, ask for a refund and view/modify is personal data.
- `cashier`: he is the cashier of the phisical cinema. He can view the upcoming events, see if there are free seats for a specific event and buy/refund a ticket for people that want to buy/refund a ticket phisically in the cinema.
- `admin`: is a privileged user. He can view/create/modify/delete rooms/shows/events.

## Project layers
As briefly described before, the project has three layers, one for the persistance, one for the backend logic and one for the web application frontend.

### MySQL Server: prsistance layer
The persistance layer is based on MySQL. Once started, two volumes are created, one for the configuration and one for the data, linked then to the container, that consists in a mysql image.

### Spring Boot: backend layer
The backend layer is based on Spring Boot. There is the core of the application, including request management, data types definitions and so on. The layer communicates directly with the persistance one via jdbc.

### Angular: frontend layer
The frontend is based on Angular. The application sends requests to the backend layer. This one will process those requests, mainly interacting with the persistance layer, and send back responses to the frontend layer. Both request and responses from/to backend and frontend are in JSON format.