## Team: Hunger Game 

team members: Zhuqing Wang, Ayushi Agrawal, Ravi Raj Pedada, Deepesh Bathija

## summary of code

The system is built using Spring Boot and utilizes a microservices architecture with RESTful APIs, a MongoDB Atlas database, and Docker Swarm for container orchestration. It is deployed on Amazon Elastic Compute Cloud (EC2) and includes a gateway, broker, and food franchises as well as branch stores. The system allows NGOs to request food information from multiple franchises and receives information from branch stores before returning a summary of available food to the NGO. The data is also stored in the MongoDB Atlas database for record-keeping and analysis. 

## Server-side usage

run the script at the root directory:

~~~
mvn compile package && docker-compose up --build
~~~

Cloud service link: http://54.216.203.127:8080/applications 

copy this link to postman or browser and request food information.

**NB: UCD campus wifi may not access this service with some unknown reasons.**

## Client-side usage

~~~
cd client
mvn spring-boot:run
~~~

## Project report 

root directory

## Demo video

https://drive.google.com/file/d/1P998Aiqomfe-RT-0rkRoBnfZoEn95Q_u/view?usp=sharing