
# RESTful API FOR WORKERS MANAGEMENT

This API provides functionality for managing workers data.

Task for nova serve.


## Steps to Setup

**1. Clone the application**

```bash
  https://github.com/ValeriiaHuza/Worker-Rest-Api
```

**2. Database Setup**

This application uses PostgreSQL as the database. Before running the application, ensure that PostgreSQL is installed on your system and create a database for the application. Follow these steps:

- **Install PostgreSQL:** If PostgreSQL is not already installed, download and install it from the official website: https://www.postgresql.org/download/

- **Create Database**

```bash
  create database worker_api_db
```

- **Or run SQL Script:** [worker_script](https://github.com/ValeriiaHuza/Worker-Rest-Api/blob/main/worker_script) script

**3. Change Database Configuration**

- open `src/main/resources/application.properties`

- change `spring.datasource.username` and `spring.datasource.password`

**4. Build and run the app using maven**

```bash
  mvn clean spring-boot:run
```

The app will start running at http://localhost:8080.

## Model Description

### Worker Entity

The **`Worker`** entity represents a worker in the system.

### Worker Fields

- ID:
   - Type: Integer
   - Description: Unique identifier for the worker.
- First Name:
   - Type: String
   - Constraints:
       - Required, not blank
       - Maximum length: 45 characters
- Last Name:
    - Type: String
    - Constraints:
        - Required, not blank
        - Maximum length: 45 characters
- Email:
    - Type: String
    - Constraints:
        - Required, not blank
        - Valid email format: valid_email_format@domain.com
        - Maximum length: 60 characters
        - Validation: @Email(regexp = "^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not valid!")
- Birth Date:
    - Type: LocalDate
    - Constraints:
        - Required
        - Must be earlier than the current date
- Position Job:
    - Type: String
    - Constraints: None specified

### PhoneNumber Entity

The **`PhoneNumber`** entity represents a phone number associated with a worker.

- ID:
  - Type: Integer
  - Description: Unique identifier for the phone number.
- Worker Id:
  - Type: Integer
  - Description: The worker id to whom this phone number belongs.
- Phone Number:
  - Type: String
  - Constraints:
    - Required, not blank
    - Maximum length: 20 characters

## Rest API

#### Get Workers List

Description: Retrieves a list of workers based on optional filtering criteria.

```http
  GET /api/workers
```
- Query Parameters:
    - firstName (optional): Filter workers by first name.
    - lastName (optional): Filter workers by last name.
    - birthday (optional): Filter workers by birth date.
    - email (optional): Filter workers by email.
    - positionJob (optional): Filter workers by job position.

- Response:
   - Status: 200 OK
   - Body: List of workers objects as JSON

#### Get Worker by ID

```http
  GET /api/workers/{workerId}
```

- Path Variable:
  - workerId: Integer (Path variable) - ID of the worker to retrieve.
- Response:
   - Status: 
       - 200 OK
       - 404 Not found: User not found
   - Body: User object as JSON (If it was found)

#### Create worker

```http
  POST /api/workers
```

- Request Body: Worker object (JSON)
- Response:
   - Status:
       - 201 Created: Worker added successfully.
       - 400 Bad Request: Unable to add Worker (validation error or other issues)
    - Body: Worker object (if created successfully)

#### Update worker by id

```http
  PUT /api/workers/{workerId}
```

- Parameters:
   - workerId: Integer (Path variable) - ID of the worker to update.
- Request Body: Worker object (JSON)
- Response:
    - Status: 
        - 200 OK
        - 400 Bad Request: Unable to add Worker (validation error or other issues)
    - Body: Worker object (if updated  successfully)

#### Delete a user by ID

```http
  DELETE /api/workers/{workerId}
```

- Parameters:
   - userId: Integer (Path variable) - ID of the worker to delete.
- Response:
   - Status: 204 No content

