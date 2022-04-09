## ecommerce-backend
* Deal with backend server applications related to e-commerce
* Will be used with other projects[spring-cloud-infra-v2, spring-cloud-config-server]

## Project structure

### core
* Common utilities

### domain
* Common entities

### auth-service
* A service for user authentication and authorization 

### product-service
* A service for product management

### order-service
* A service for ordering products
* If you want to test [order-service], need to run on [dev] profiles.
  * Run MySQL, using the [docker-compose.yml] file.

### payment-service 
* A service for paying for products

### Diagram
* Order the products
  ![class-diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/laonzenamoon/ecommerce-backend/master/docs/order-products.puml)

## Skills
* Java 11
* Spring Boot 2.5.x
* Spring Boot Actuator
* Spring Cloud
  * Spring Cloud Netflix Eureka
  * Spring Cloud Gateway
  * Spring Cloud Config
  * HTTP Request 
    * Feign Client
  * Circuit Breaker
    * Resilience4j
  * Distributed Transaction
    * Spring Cloud Zipkin
    * Spring Cloud Sleuth
* Messaging service
  * [Kafka](https://github.com/wurstmeister/kafka-docker)
* Docker
  * docker-compose
* MySQL
* Swagger
* PlantUML
* p6spy
