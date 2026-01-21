# Project Installation Guide

Follow these simple steps to set up and run the **Food Ordering Platform** on a new machine.

## 1. Required Tools & Downloads

You need to install the following software before running the application.

------------------------------------------------------------

### Java Development Kit (JDK) 17
This project requires Java 17.
- **Download Link:** [Oracle JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK 17](https://jdk.java.net/archive/)
- **Installation:** Download the installer for your OS (Windows/Mac/Linux) and run it.
- **Verify:** Open a terminal/command prompt and run:
  ```
  java -version
  ```

### Apache Maven
Used for building the project and managing dependencies.
- **Download Link:** [Apache Maven](https://maven.apache.org/download.cgi)
- **Installation:**
    1. Download the `Binary zip archive`.
    2. Extract it to a folder (e.g., `C:\Program Files\Maven`).
    3. Add the `bin` folder path to your System Environment Variables (`PATH`).
- **Verify:**
  ```
  mvn -version
  ```

### MySQL Server
Relational database storage.
- **Download Link:** [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) or [mysql workbench](https://dev.mysql.com/downloads/workbench/)
- **Installation:** Download and install the MySQL Installer. During setup, choose a root password.
- **Configuration:**
    - **Port:** Default `3306`
    - **Username:** `root`
    - **Password:** `root` (Update `src/main/resources/application.properties` if you choose a different password).

------------------------------------------------------------

## 2. Project Setup

### Step 1: Clone or Extract Project
Copy the project folder to your desired location.

### Step 2: Configure Database
Ensure your MySQL server is running. The application is configured to automatically create the database `foododeringplatform` if it doesn't exist.
- **Check Credentials:** Open `src/main/resources/application.properties` and ensure these match your local MySQL setup:
  ```
  spring.datasource.username=root
  spring.datasource.password=root
  ```

### Step 3: Install Dependencies
Open a terminal in the project root folder (where `pom.xml` is located) and run:
``` 
mvn clean install
```
This will download all necessary libraries.

------------------------------------------------------------

## 3. Running the Application

### Option 1: Using Command Line
Run the following command in the project root:
``` 
mvn spring-boot:run
```

### Option 2: Using an IDE
- Open the project in **IntelliJ IDEA**, **Eclipse**, or **VS Code**.
- Locate the main class: `com.foododeringplatform.foododeringplatform.FoodorderingplatformApplication`.
- Right-click and select **Run**.

------------------------------------------------------------

## 4. Access the Application

Once the application starts successfully, open your browser and go to:
**[http://localhost:8081]**

------------------------------------------------------------

## Troubleshooting
- **Port 8081 is busy?** Change `server.port=8081` in `application.properties` to a different number (e.g., `8082`).
- **Database Connection Error?** unexpected error? Check your MySQL username/password and ensure the MySQL service is running.

------------------------------------------------------------
