# SE1919 - Workshop 2: Product Introduction Website

A Java EE web application for managing and showcasing products, built with NetBeans, Servlets, JSP, JPA (EclipseLink), and Microsoft SQL Server.

## Features

### Customer
- Browse, search, and filter products by name, category, price range, and discount
- Sort products by price
- View product details
- Shopping cart (add, update, remove items, checkout)
- Register and login with auto-login (cookie-based)

### Admin / Manager
- Manage products: add, update, delete, restore backup
- Manage categories: add, update, delete, search
- Manage accounts: add, update, delete, search, activate/deactivate
- Role-based access control (admin, manager, customer)

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java EE 7 |
| Web | Servlets, JSP, JSTL |
| ORM | JPA 2.1 (EclipseLink 2.7.9) |
| Database | Microsoft SQL Server |
| JDBC Driver | sqljdbc4 |
| Build | Apache Ant (NetBeans) |

## Project Structure

```
src/java/
├── controller/   # Servlets (MVC controllers)
├── filter/       # Servlet filters (auth, role checks)
├── listener/     # Application and session listeners
├── model/        # JPA entities + JPA controllers
└── utils/        # JPAUtils (EntityManagerFactory)

web/              # JSP pages
lib/              # Project libraries
```

## Prerequisites

- JDK 8+
- Apache Tomcat 8+
- Microsoft SQL Server (default: `localhost:1433`)
- NetBeans IDE (recommended)

## Setup

1. **Run the database script** — Execute `NewScript.sql` on your SQL Server instance. The script will create the `ProductIntro` database, all tables, and insert sample data (accounts, categories, products).

2. **Configure connection** — Edit `src/conf/persistence.xml` if your SQL Server credentials differ from the defaults:
   ```xml
   <property name="javax.persistence.jdbc.url" value="jdbc:sqlserver://localhost:1433;databaseName=ProductIntro"/>
   <property name="javax.persistence.jdbc.user" value="sa"/>
   <property name="javax.persistence.jdbc.password" value="12345"/>
   ```

3. **Build and deploy** — Open the project in NetBeans and run it, or build with Ant:
   ```
   ant clean build
   ```
   Then deploy the generated WAR to Tomcat.

4. **Access** — Navigate to `http://localhost:8080/<app-context>/`

### Default accounts

| Username | Password | Role |
|----------|----------|------|
| `admin`  | `abc`    | Admin |
| `manager`| `123`    | Manager |

## Author

NhanNKL — SE200019 | SE1919
