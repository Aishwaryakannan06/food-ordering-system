# Food Ordering System — Java REST API

A starter backend for the college project using Java 17, Spring Boot, Spring Data JPA, and MySQL. It provides food menu CRUD, searching/filtering, order placement, tax calculation (illustrative 5%), order history, and order-status updates.

> This is a learning/demo backend. It does not include authentication, payment processing, or production security. Review and configure it before deployment.

## Requirements
- JDK 17+
- Maven 3.8+
- MySQL 8+

## Run it
1. Start MySQL.
2. Open `src/main/resources/application.properties` and change the default password, or set environment variables `DB_USERNAME` and `DB_PASSWORD`.
3. From this folder run: `mvn spring-boot:run`
4. API base URL: `http://localhost:8080/api`

The app creates the `food_ordering` database if the configured MySQL user has permission. JPA creates/updates tables for this demo. For real deployments, use database migrations instead of `ddl-auto=update`.

## Endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/foods` | List available food |
| GET | `/api/foods?category=Meals` | Filter by category |
| GET | `/api/foods?search=burger` | Search menu |
| GET | `/api/foods/{id}` | Get one food item |
| POST | `/api/foods` | Add food item |
| PUT | `/api/foods/{id}` | Update food item |
| DELETE | `/api/foods/{id}` | Delete food item |
| POST | `/api/orders` | Place order and calculate 5% demo tax |
| GET | `/api/orders` | View order history |
| GET | `/api/orders/{id}` | View one order |
| PATCH | `/api/orders/{id}/status?status=PREPARING` | Update order status |

## Postman examples

### List menu
`GET http://localhost:8080/api/foods`

### Create an order
`POST http://localhost:8080/api/orders`
Header: `Content-Type: application/json`
```json
{
  "groupName": "College Lunch",
  "items": [
    { "foodItemId": 1, "quantity": 2, "orderedBy": "Asha" },
    { "foodItemId": 3, "quantity": 1, "orderedBy": "Ravi" }
  ]
}
```

The backend calculates the price using the current database menu prices, then returns the subtotal, illustrative 5% tax, total, order ID, and saved order lines. Do not send prices from the client.

### Add a food item
`POST http://localhost:8080/api/foods`
```json
{
  "name": "Dosa",
  "category": "Meals",
  "description": "Crispy South Indian dosa",
  "price": 75.00,
  "emoji": "🥞",
  "available": true
}
```

### Update status
`PATCH http://localhost:8080/api/orders/1/status?status=PREPARING`

## Frontend connection
The current HTML preview is a separate frontend prototype and does not call this API yet. To connect them, replace its local menu/cart/order functions with `fetch()` requests to this backend. If frontend and backend run on different ports, configure CORS to the exact frontend origin instead of `*`.

## Bill splitting
The API stores each order line's `orderedBy` name, allowing the frontend to group items per person. This starter backend stores group name and item ownership; item/equal share calculations are currently expected to be displayed by the frontend. A future version can persist group-member records and server-calculated member shares.


## Run the complete connected app (frontend + Java API + MySQL)

The browser UI is served by Spring Boot itself, so it calls the API at `/api` without needing CORS setup or a separate frontend server.

### 1. Install prerequisites
- Java 17 or newer
- Maven
- MySQL Server

### 2. Create the database / set credentials
The app attempts to create `food_ordering` automatically if the MySQL account has permission. Otherwise, run `CREATE DATABASE food_ordering;` in MySQL.

Set your MySQL username/password before starting. Windows Command Prompt example:
```bat
set DB_USERNAME=root
set DB_PASSWORD=YOUR_MYSQL_PASSWORD
```
Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password. Do not share it. You can also update `src/main/resources/application.properties` locally.

### 3. Start the app
Open Command Prompt in the folder containing `pom.xml`, then run:
```bat
mvn spring-boot:run
```
Open **http://localhost:8080** in Chrome. Do not open `index.html` by double-clicking it; use the localhost address so the Java API is available.

### 4. Demonstrate the full flow
1. Menu is loaded from `GET /api/foods` (the Java app seeds six foods on first run).
2. Add dishes and assign them to group members in the UI.
3. Select **Place order**. The browser sends `POST /api/orders`.
4. Java validates the items and calculates subtotal, 5% tax and total on the server.
5. The order is saved in MySQL and the confirmation shows the database order ID and server totals.
6. To show stored orders, use Postman `GET http://localhost:8080/api/orders`.

### API endpoints
- `GET /api/foods` — menu
- `GET /api/foods?search=burger` — search
- `POST /api/orders` — save order
- `GET /api/orders` — list saved orders
- `GET /api/orders/{id}` — one saved order
- `PATCH /api/orders/{id}/status?status=PREPARING` — update status

### Important limitations
This is a college demo, not a production food delivery platform. No login, payment gateway, delivery tracking, or persistent per-member split amounts are included. The selected dish owner is saved with each order line, while group shares are still calculated in the UI. Never use real payment or personal data for the demo.
