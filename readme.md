
# SpringCommerce Online Shopping

### This is a simple web application built using Java Spring Boot framework. The application allows users to browse and search for products, add them to a shopping cart, and place orders. The application supports cash payment upon product delivery.

## Software Development Principles, Patterns, and Practices

The application follows the MVC Architecture (Model-View-Controller architectural pattern), separating the business logic (model), presentation layer (view), and user interaction (controller). The application exposes RESTful APIs to perform CRUD operations on products, carts and orders.

## Code Structure
The codebase is organized into the following main directories:

- src/main/java: Contains the Java source code files.
    - com.example.gk: Root package for the application.
        - controller: Contains the controllers for handling HTTP requests and mapping them to appropriate methods.
        - model: Contains the entity classes representing products, carts and orders.
        - repositorie: Contains the repositories for database operations.
        - service: Contains the business logic services for handling product and order operations.
    - resources: Root package for the template.
        - static: Contains static files such as CSS, JavaScript, images used in the user interface.
        - template: Contains template files used to create user interfaces.

![Code Structure](https://github.com/52100799/images/blob/main/0.png?raw=true)

## Getting Started

To run the application on your local computer, follow these steps:

- Ensure that you have JDK17 installed on your machine.
- Clone this repository to your local machine.
- Create a new database on MySQL and name it springcommerce.
- Import springcommerce.sql file.
- If you have set a password for the database, update the password in the application.properties file accordingly.
- Open the GkApplication.java file in IntelliJ IDEA and run the application.
- Once the application is running, you can access it in your web browser at http://localhost:8081.

## API

#### Get all products
```http
  GET /api/products
```
![Get all products](https://github.com/52100799/images/blob/main/1.png?raw=true)


#### Get a product by id
```http
  GET /api/products/{id}
```
![Get a product by id](https://github.com/52100799/images/blob/main/2.png?raw=true)


#### Create a product
```http
  POST /api/products
```
![Create a product](https://github.com/52100799/images/blob/main/3.png?raw=true)


#### Update a product by id
```http
  UPDATE /api/products/{id}
```
![Update a product by id](https://github.com/52100799/images/blob/main/4.png?raw=true)


#### Delete a product by id
```http
  DELETE /api/products/{id}
```
![Delete a product by id](https://github.com/52100799/images/blob/main/5.png?raw=true)


#### Filter products by price
```http
  GET /api/products/price-range?minPrice={minPrice}&maxPrice={maxPrice}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `minPrice` | `double` | **Required**. min price to filter |
| `maxPrice` | `double` | **Required**. max price to filter |

![Filter products by price](https://github.com/52100799/images/blob/main/6.png?raw=true)



#### Filter products by color
```http
  GET /api/products/products/color?color={color}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `color` | `string` | **Required**. color to filter |

![Filter products by color](https://github.com/52100799/images/blob/main/7.png?raw=true)


#### Get all carts
```http
  GET /api/carts
```
![Get all carts](https://github.com/52100799/images/blob/main/8.png?raw=true)


#### Get a cart by id
```http
  GET /api/carts/{id}
```
![Get a cart by id](https://github.com/52100799/images/blob/main/9.png?raw=true)


#### Create a cart
```http
  POST /api/carts
```
![Create a cart](https://github.com/52100799/images/blob/main/10.png?raw=true)


#### Update a cart by id
```http
  UPDATE /api/carts/{id}
```
![Update a cart by id](https://github.com/52100799/images/blob/main/11.png?raw=true)


#### Delete a cart by id
```http
  DELETE /api/carts/{id}
```
![Delete a cart by id](https://github.com/52100799/images/blob/main/12.png?raw=true)


#### Get all orders
```http
  GET /api/orders
```
![Get all orders](https://github.com/52100799/images/blob/main/13.png?raw=true)


#### Get an order by id
```http
  GET /api/orders/{id}
```
![Get an order by id](https://github.com/52100799/images/blob/main/14.png?raw=true)


#### Create an order
```http
  POST /api/orders
```
![Create an order](https://github.com/52100799/images/blob/main/15.png?raw=true)


#### Update an order by id
```http
  UPDATE /api/orders/{id}
```
![Update an order by id](https://github.com/52100799/images/blob/main/16.png?raw=true)


#### Delete an order by id
```http
  DELETE /api/orders/{id}
```
![Delete an order by id](https://github.com/52100799/images/blob/main/17.png?raw=true)