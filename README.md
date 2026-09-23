# Library Management System

A full-stack Library Management System built with **Spring Boot, Spring Data JPA, MySQL, and Angular**.

The application provides REST APIs for managing books, members, and loans, along with an Angular frontend for interacting with the system.

## Features

### Book Management

- Add new books
- View books with pagination
- Search books by title, author, or ISBN
- Update book information
- Delete books
- Track total and available copies
- Prevent deletion when books are currently borrowed or have loan history

### Member Management

- Add members
- View members with pagination
- Search members
- Update member information
- Delete members
- Email validation
- Unique email constraint

### Loan Management

- Borrow books
- Return books
- Track borrow, due, and return dates
- Track loan status
- Automatically update available book copies
- Prevent borrowing when no copies are available

### Frontend

- Dashboard with library statistics
- Responsive UI
- Form validation
- Loading states
- Error handling with retry functionality
- Pagination
- Search
- Confirmation dialogs for destructive actions
- User-friendly date formatting

### Backend

- RESTful APIs
- Layered architecture
- DTO-based request/response handling
- Spring Data JPA
- Global exception handling
- Bean validation
- Business-rule validation
- Pagination and search
- Unit testing with JUnit and Mockito

---

## Tech Stack

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- JUnit 5
- Mockito

### Frontend

- Angular
- TypeScript
- HTML
- CSS

### Development Tools

- IntelliJ IDEA
- Visual Studio Code
- Postman
- Git & GitHub

---

## Architecture

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

The Angular frontend communicates with the backend through REST APIs:

```text
Angular Frontend
       ↓
   REST APIs
       ↓
Spring Boot Backend
       ↓
Spring Data JPA
       ↓
     MySQL
```

---

## Project Structure

```text
Library-Management-System/
│
├── Backend/
│   └── library-management-system/
│       ├── src/
│       │   ├── main/
│       │   │   └── java/
│       │   └── test/
│       └── pom.xml
│
├── Frontend/
│   └── library-management-frontend/
│       ├── src/
│       ├── angular.json
│       ├── package.json
│       └── tsconfig.json
│
└── README.md
```

---

## Core API Endpoints

### Books

| Method | Endpoint            | Description         |
| ------ | ------------------- | ------------------- |
| POST   | `/api/books`        | Add a book          |
| GET    | `/api/books`        | Get paginated books |
| GET    | `/api/books/{id}`   | Get book by ID      |
| PUT    | `/api/books/{id}`   | Update a book       |
| DELETE | `/api/books/{id}`   | Delete a book       |
| GET    | `/api/books/search` | Search books        |

### Members

| Method | Endpoint              | Description           |
| ------ | --------------------- | --------------------- |
| POST   | `/api/members`        | Add a member          |
| GET    | `/api/members`        | Get paginated members |
| GET    | `/api/members/{id}`   | Get member by ID      |
| PUT    | `/api/members/{id}`   | Update a member       |
| DELETE | `/api/members/{id}`   | Delete a member       |
| GET    | `/api/members/search` | Search members        |

### Loans

| Method | Endpoint                 | Description      |
| ------ | ------------------------ | ---------------- |
| POST   | `/api/loans`             | Borrow a book    |
| PUT    | `/api/loans/{id}/return` | Return a book    |
| GET    | `/api/loans`             | Get loan history |

---

## Business Rules

The application implements several business rules to maintain data consistency.

### Book Copies

When a book is borrowed:

```text
availableCopies = availableCopies - 1
```

When a book is returned:

```text
availableCopies = availableCopies + 1
```

A book cannot be borrowed when:

```text
availableCopies = 0
```

### Book Updates

When updating the total number of copies, existing borrowed copies are preserved.

For example:

```text
Total Copies = 5
Available Copies = 3

Borrowed Copies = 5 - 3
                 = 2
```

The total number of copies cannot be changed to a value lower than the number currently borrowed.

### Book Deletion

A book cannot be deleted if:

- Copies are currently borrowed
- The book has existing loan history

### Member Email

Member email addresses must be:

- Valid email addresses
- Unique within the system

---

## Error Handling

The backend uses centralized exception handling for consistent API responses.

Examples include:

- `400 Bad Request` — Validation errors
- `404 Not Found` — Resource does not exist
- `409 Conflict` — Business-rule or duplicate-data conflicts

Example error response:

```json
{
  "timestamp": "2026-09-24T10:30:00",
  "status": 409,
  "message": "Email already exists",
  "errors": []
}
```

---

## Testing

The backend includes unit tests using:

- JUnit 5
- Mockito

Tests cover service and controller behavior including:

- Successful operations
- Resource-not-found scenarios
- Validation scenarios
- Business-rule failures
- Update behavior
- Delete restrictions

---

## Running the Backend

### 1. Clone the repository

```bash
git clone https://github.com/MitraSrijon/Library-Management-System.git
cd Library-Management-System
```

### 2. Configure MySQL

Create a MySQL database and update the Spring Boot database configuration with your own credentials.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Do not commit real database credentials to GitHub.

### 3. Start the backend

Navigate to:

```bash
cd Backend/library-management-system
```

Run:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

---

## Running the Frontend

Navigate to:

```bash
cd Frontend/library-management-frontend
```

Install dependencies:

```bash
npm install
```

Start Angular:

```bash
ng serve
```

The frontend runs on:

```text
http://localhost:4200
```

---

## Application Flow

```text
Dashboard
   │
   ├── Books
   │     ├── Add
   │     ├── Search
   │     ├── Edit
   │     └── Delete
   │
   ├── Members
   │     ├── Add
   │     ├── Search
   │     ├── Edit
   │     └── Delete
   │
   ├── Borrow Book
   │     └── Create Loan
   │
   └── Loans
         ├── View Loan History
         └── Return Book
```

---

## Key Learning Outcomes

This project was built to practice and demonstrate:

- Designing REST APIs with Spring Boot
- Layered backend architecture
- Spring Data JPA and database interaction
- DTOs and validation
- Exception handling
- Business-rule implementation
- Pagination and search
- Unit testing with JUnit and Mockito
- Angular component-based development
- Angular services and HTTP communication
- Frontend form validation
- Full-stack integration
- Git and GitHub workflow

---

## Future Improvements

Potential future improvements include:

- Authentication and authorization
- Role-based access control
- JWT-based security
- Advanced reporting
- Fine calculation for overdue books
- Email notifications
- Dockerization
- CI/CD pipeline
- Cloud deployment

---

## Author

**Srijon Mitra**

GitHub: [MitraSrijon](https://github.com/MitraSrijon)

---

## License

This project is created for learning and portfolio purposes.
