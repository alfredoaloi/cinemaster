# cinemaster
A cinema manager project, provided with a MySQL Server persistance layer, a Spring Boot backend layer and an Angular frontend layer

## Application features
The application is a cinema manager. There are four types of user:
- `guest`: is a simple user that is not logged-in. He can view upcoming events and see if there are free seats for specific events
- `user`: is a guest that is logged in. He starts with the same privileges of `guest`. Moreover, he can buy tickets, see a list of bought tickets, ask for a refund and view/modify is personal data
- `cashier`: he is the cashier of the phisical cinema. He can view the upcoming events, see if there are free seats for a specific event and buy/refund a ticket for people that want to buy/refund a ticket phisically in the cinema
- `admin`: is a privileged user. He can view/create/modify/delete rooms/shows/events

## Project layers
As briefly described before, the project has three layers, one for the persistance, one for the backend logic and one for the web application frontend

### MySQL Server: prsistance layer
The persistance layer is based on MySQL. Once started, two volumes are created, one for the configuration and one for the data, linked then to the container, that consists in a mysql image

### Spring Boot: backend layer
The backend layer is based on Spring Boot. There is the core of the application, including request management, data types definitions and so on. The layer communicates directly with the persistance one via jdbc

### Angular: frontend layer
The frontend is based on Angular. The application sends requests to the backend layer. This one will process those requests, mainly interacting with the persistance layer, and send back responses to the frontend layer. Both request and responses from/to backend and frontend are in JSON format

## How to containerize
To run the project you must have installed Docker and Docker Compose. After this step, just open a terminal in the folder of the project (the same level of the file `docker-compose.yml`) and run:
`docker-compose up --build`

Wait until the three layers are set up and then visit http://localhost:4200/home

## How to deploy on Minikube
To deploy the application to a Kubernetes local cluster, like Minikube, you must have installed Minikube and kubectl. Then, just start the server with `minikube start` and run `kubectl apply -f generated` to deploy to the local cluster. Wait until the services are set up, open two terminal and run:
- `kubectl port-forward service/backend 8080:8080` to enable port-forwarding of backend service to http://localhost:8080
- `kubectl port-forward service/frontend 4200:4200` to enable port forwarding of frontend service to http://localhost:4200

Instead of port-forward services, you may expose services on an external ip. First of all you need to delete the two services running:
- `kubectl delete service backend`
- `kubectl delete service frontend`
Next, expose the services running:
- `kubectl expose deployment backend --type=LoadBalancer --name=backend --port=8080`
- `kubectl expose deployment frontend --type=LoadBalancer --name=frontend --port=4200`
In case of a minikube cluster you have to run `minikube tunnel`, in order to avoid the endless `PENDING` status of the exposed services

Visit http://localhost:4200/home and you should navigate the webisite

Note: all contents in the minikube folder were generated using the tool [kompose](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/) that translates a docker-compose file in a set of `.yaml` files, used by kubectl to deploy something on the local cluster

## Ingress Controller using Kong
Kubernetes Ingress Controller can be useful to expose different mmicroservices under the same host, using different paths. In this project the Ingress object definition can be found in the folder `kong`. First of all, you need to have installed and configured Kong Gateway, then just run:
- `kubectl apply -f kong`

Visit http://localhost/home and you should navigate the webisite
Moreover, you can perform backend requests at http://localhost/backend/

Note: to make it work, like ypu did it before, you need to expose backend service on an external ip. First of all you need to delete the backend service, running:
- `kubectl delete service backend`
Next, expose the service, running:
- `kubectl expose deployment backend --type=LoadBalancer --name=backend --port=8080`