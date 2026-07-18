# Shoplane – Full-Stack E-Commerce Application

Shoplane is a complete, full-stack e-commerce marketplace web application engineered using a robust Java, Spring Boot, and Maven backend paired with a functional, JavaScript, HTML5, and CSS3 frontend interface. The ecosystem seamlessly processes user sessions, interactive product inventory catalogs, relational shopping cart states, secure checkouts, and cross-origin payment verification gateways.

## 🚀 Tech Stack & Core Dependencies

### Frontend Client Layer
*   **Structure & Styling:** HTML5 / CSS3 (Responsive Viewports)
*   **Client Logic & Data Binding:** JavaScript
*   **Network Request Gateway:** Native Browser Fetch API

### Backend Server Layer
*   **Build Tool & Dependency Manager:** Maven
*   **Language & Framework:** Java  / Spring Boot
*   **Data Layer:** Spring Data JPA / Hibernate
*   **Database Engine:** MySQL Server 
*   **Security Architecture:** Cryptographic BCrypt Password Hashing
*   **Payment Gateway API:** Razorpay Integration (`razorpay-java` SDK)
*   **Notification Engine:** Spring Boot Email Service (Gmail SMTP via JavaMailSender)

---

## 📂 Project Architecture
The application is structured as a decoupled monorepo, keeping client presentation views cleanly isolated from backend 3-tier architectures (Controller, Service, Repository) to model real-world engineering standards [2026 Guide].

```text
Shoplane/
├── shoplane-frontend/       # Client UI Assets Layer
│   ├── index.html           # Main Marketplace Landing View
│   ├── login.html           # Authentication View
│   ├── styles.css           # Custom Component Styling Layouts
│   └── script.js            # Asynchronous Fetch Client & Cart Controllers
│
└── shoplane-backend/        # Server Core Application Layer
    ├── src/main/java/com/cfs/Ecomm/
    │   ├── controller/      # REST API Gateway Endpoints (CORS Enabled)
    │   ├── service/         # Enterprise Core Business Logic Processors
    │   ├── repo/            # Spring Data JPA Relational Connectors
    │   ├── model/           # JPA Relational Schema Database Entities
    │   └── dto/             # Lightweight Network Request/Response Payloads
    └── pom.xml              # Maven Project Configurations
```

---

## ✨ Key Features & Technical Implementations

### 1. Functional Frontend UI & Client Session Workflows
*   **Interactive Shopping Carts:** Uses JavaScript to dynamically calculate, increment, and persist product totals inside browser local storage arrays without requiring jarring page reloads.
*   **Authentication & Logout Pipelines:** Captures form payloads to execute user login, registration, and session token drops seamlessly across the presentation view.

### 2. Secure Backend Authentication Layer
*   **BCrypt Hashing:** Safely encodes plain-text user passwords using a strong cryptographic hash before storing them in the database to prevent credentials exposure.
*   **Duplicate Prevention:** Implements pre-registration transactional checks to reject emails that are already associated with active accounts.

### 3. Streamlined Checkout & Razorpay Integration
*   **CORS Enabled:** Implements `@CrossOrigin("*")` at the controller level to allow seamless frontend cross-origin network data transfers without browser blockage.
*   **Asynchronous Email Invoicing:** Integrates an asynchronous mail sub-engine that dispatches clean purchase receipts to customer emails immediately upon a successful database payment verification commit.

### 4. Query Performance Optimization
*   Utilizes custom JPQL and `JOIN FETCH` operations inside the repository layer to fetch nested entity relationships in a single database join query, actively eliminating the N+1 select performance problem during database scans.

---

## 🎟️ Endpoint Specifications & API Documentation

### 👤 User Services (`/users`)
*   `POST /register` - Registers a new customer into the system (includes password hashing checks).
*   `POST /login` - Validates credentials against storage and returns an optimized `UserResponseDto`.

### 🛒 Product Services (`/products`)
*   `GET /` - Fetches the entire active item marketplace catalog.
*   `GET /search?query={keyword}` - Dynamic lookup utilizing flexible item title and detail matching.

### 💳 Payment Services (`/payments`)
*   `POST /create` - Accepts a `PaymentRequestDto` payload and returns the raw Razorpay Order ID to launch the client checkout overlay interface.

---

## 🛠️ Local Setup & Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com
   cd Shoplane
   ```

2. **Configure Backend Credentials:**
   Open `shoplane-backend/src/main/resources/application.properties` and add your MySQL and SMTP keys [2026 Guide]:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommDB
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.mail.username=your_admin_email@gmail.com
   spring.mail.password=your_gmail_app_password
   razorpay.key.id=your_razorpay_key_id
   razorpay.key.secret=your_razorpay_key_secret
   ```

3. **Launch the Application:**
   *   **Backend:** Navigate to `shoplane-backend` and run: `mvn clean install` followed by `mvn spring-boot:run`.
   *   **Frontend:** Simply double-click `shoplane-frontend/index.html` to open your interactive interface in any web browser!
