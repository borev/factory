<h1 align="center">
  Manufacturing company
</h1>

<p align="center">
<a alt="Java">
<img src="https://img.shields.io/badge/Java-v11-orange.svg" />
</a>
<a alt="Spring Boot">
<img src="https://img.shields.io/badge/Spring%20Boot-v2.6.4-brightgreen.svg" />
</a>
</p>

## Problem Overview ##

A Manufacturing company has various warehouses in multiple cities. When an order for a product is placed by their
customers, the company wants to make sure the order is picked up from the warehouse that is closest to the customers
location and containing the product.

**Ask**

Build a microservice with a REST API based interface to solve the above problem. There should be at least two key APIs
one for adding of a new customer and the other to place of an order. The order placement API in the response should
indicate from which warehouse the product would be delivered and the time/distance for arrival. The expectation is that
the response time of order placement is fairly quick (< 500ms).

**Assumptions**

Assume that the distance/time between the customers city/location and warehouse is provided when customer is setup. The
system would compute the using the data that is provided during setup. The format of the data can be anything as per the
solution.

Assume a certain number of Warehouses & products to be present. Assume any structure for the data. Preferably represent
that in JSON format as static data (or stored in db) that can be used for computation.

**Technology requirements**

- Build the solution using Java
- Preferably build the service using Sprint Boot
- Usage of database is not necessary
- Submit a brief write up of any key decisions being made
- Bonus points for writing unit test cases (key scenarios)


## General solution ##

To quickly place an order, you need to calculate in advance all the distances to the warehouses
I will do this asynchronously (it can be message broker) when adding a new customer.
Since at the time of ordering not all distances can be calculated we can directly calculate those that are missing
## Running the server ##

```
./gradlew bootRun
```

## API endpoints ##

- http://localhost:8080/swagger-ui/index.html

```
GET /api/v1/products
```
```
POST /api/v1/customers
```
```
POST /api/v1/orders
```

