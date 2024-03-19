# Ecommerce Handicraft Backend API

This is the backend API for the Ecommerce Handicraft project. It provides endpoints for managing user authentication, cart management, product listings, orders, and more.

## Table of Contents
- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Endpoints](#endpoints)
- [Setup Instructions](#setup-instructions)
- [Contact Information](#contact-information)

## Introduction

The Ecommerce Handicraft project aims to create an online platform for buying and selling handicraft products. This backend API serves as the core component of the project, providing the necessary functionality for users to interact with the system.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- JWT (JSON Web Tokens)

## Features

- User authentication and authorization
- CRUD operations for products
- Cart management
- Order processing
- User profile management

## Endpoints

The following are the main endpoints provided by this API:

- **Authentication**:
    - `/auth/signup`: Create a new user account.
    - `/auth/login`: Authenticate user and generate JWT token.

- **Product Management**:
    - `/api/products`: Get all products or add a new product.
    - `/api/products/{id}`: Get, update, or delete a specific product by ID.

- **Cart Management**:
    - `/api/cart`: Find user's cart by JWT token.
    - `/api/cart/add`: Add an item to the user's cart.

- **Order Management**:
    - `/api/orders`: Get all orders or place a new order.
    - `/api/orders/{id}`: Get details of a specific order by ID.

## Setup Instructions

To set up the project locally, follow these steps:

1. Clone the repository to your local machine.
2. Ensure you have Java, MySQL, and Maven installed.
3. Configure the `application.properties` file with your MySQL database credentials.
4. Run the database migration scripts to create the necessary tables.
5. Build and run the project using Maven or your preferred IDE.

## Contact Information

For any inquiries or assistance regarding this project, feel free to contact:

**Nabendu Bikash Binda**  
**Email:** [binda.nabendu@gmail.com]()
Visit: [nabendu.vercel.app]()

Your feedback and suggestions are highly appreciated!
