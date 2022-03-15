# Manufactory

## General solution ##

To quickly place an order, you need to calculate in advance all the distances to the warehouses
I will do this asynchronously (it can be message broker) when adding a new customer.
Since at the time of ordering not all distances can be calculated we can directly calculate those that are missing
## Running the server locally ##

```
./gradlew bootRun
```

## API definition ##

- http://localhost:8080/swagger-ui/index.html

