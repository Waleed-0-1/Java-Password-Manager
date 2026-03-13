# Secure Java Password Vault 

A persistent, database-backed password management system developed as part of my 2nd-semester Cyber Security studies at **UET Lahore**.

## Overview
This project bridges Java OOP principles with relational database management. It replaces insecure local file storage with a centralized **MySQL** database, focusing on data integrity and secure authorization.

##  Key Features
* **Database Persistence:** Full CRUD integration using **JDBC**.
* **Two-Tier Authorization:** Session-level Master Password and record-level 4-digit PINs.
* **SQL Injection Protection:** Implementation of `PreparedStatement` to sanitize user inputs.
* **Session Optimization:** Uses `HashMap` for efficient $O(1)$ data retrieval.

##  Tech Stack
* **Language:** Java 25 (JDK)
* **Database:** MySQL 8.0
* **Driver:** MySQL Connector/J
* **IDE:** VS Code

## How to Run
1. Clone the repository.
2. Import the `vault_db.sql` schema into your MySQL server.
3. Update `DB_PASS` in `DatabaseManager.java` with your local credentials.
4. Compile and run `Main.java`.