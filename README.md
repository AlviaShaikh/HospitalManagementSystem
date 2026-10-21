# Hospital Management System

A mini academic project using **Java Swing**, **MySQL**, and **JDBC** only. It follows a lightweight MVC/DAO architecture and can be opened in NetBeans, Eclipse, or run with Maven.

## Quick start

1. Install JDK 17+, MySQL 8+, and Maven 3.9+.
2. Run `mysql -u root -p < db/schema.sql`.
3. Copy `src/main/resources/application.properties.example` to `src/main/resources/application.properties` and set the MySQL credentials. Environment variables `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` override the file.
4. Run `mvn clean compile exec:java`.
5. Log in with `admin / admin123` or `reception / reception123`.

The seed data includes three doctors and two patients. Use **Completed** appointment status before issuing a billing slip. `db/schema.sql` contains all foreign keys, indexes, generated total logic, and sample data.

## Project documents

- `docs/PRD.md`: academic Product Requirements Document.
- `docs/TRD.md`: academic Technical Requirements Document.
- `docs/WORKFLOW.md`: exact user and data workflow.
- `db/schema.sql`: database DDL and seed data.

## Security note

Passwords are stored as SHA-256 hashes for an educational local project. A production hospital system should use salted adaptive password hashing, role-based permissions, audit logs, encryption, backups, and formal privacy/compliance controls.
"# HospitalManagementSystem" 
"# HospitalManagementSystem" 
