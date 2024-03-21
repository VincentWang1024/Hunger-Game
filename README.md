# The Hunger Games

Team members: Zhuqing Wang, Ayushi Agrawal, Ravi Raj Pedada, Deepesh Bathija

## Description

This project aims to reduce food waste and combat hunger by connecting food chain franchises with NGOs. By using restful APIs published on the franchises' websites, NGOs can access information about excess food at the end of the day and use it to distribute to those in need.
  
The system is built using Spring Boot and utilizes a microservices architecture with RESTful APIs, a MongoDB Atlas database, and Docker Swarm for container orchestration. It is deployed on Amazon Elastic Compute Cloud (EC2) and includes a gateway, broker, food franchises, and branch stores. The system allows NGOs to request food information from multiple franchises and receives information from branch stores before returning a summary of available food to the NGO. The data is stored in the MongoDB Atlas database for record-keeping and analysis.

## Distributed Systems 

This project utilizes a distributed system architecture with multiple microservices communicating with each other through RESTful APIs. The use of Docker Swarm allows for easy deployment and scaling of the system across multiple machines. The use of a MongoDB Atlas database allows for data storage and management in a distributed environment.

## Server-side Usage

To run this project, we can clone the repository and navigate to the project's root directory. Run the following command to compile and run the project:

~~~
mvn compile package
docker-compose up --build
~~~

Cloud service link: http://54.216.203.127:8080/applications 

copy this link to postman or browser and request food information.

*NB: UCD campus wifi may not access this service with some unknown reasons.*

## Client-side Usage

~~~
cd client
mvn spring-boot:run
~~~
