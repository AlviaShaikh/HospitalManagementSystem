# Technical Requirements Document (Academic Synopsis)

## Architecture
The application uses a layered MVC/DAO design. Swing views are in `ui`; immutable records are in `model`; interfaces are in `dao`; JDBC implementations are in `dao.impl`; validation/use-case boundaries are in `service`; connection configuration is in `config`; shared hashing and UI helpers are in `util`.

## Technology stack
- Java 17 and standard Swing/AWT.
- MySQL 8+.
- JDBC through MySQL Connector/J 9.0.0.
- Maven for dependency and build management.
- No web server, ORM, frontend framework, or non-MySQL database.

## Runtime flow
`HospitalManagementApplication -> LoginFrame -> AuthService -> UserDaoImpl -> DatabaseConnection`. A successful login opens `MainFrame`, which creates services and panels. Each panel calls a service; each service delegates persistence to a DAO interface; each JDBC DAO creates a connection and runs parameterized SQL.

## Database design
`users` stores login identities. `patients` stores demographic/contact information. `doctors` stores specialization, department, availability, and active state. `appointments` references one patient and one doctor and optionally the creating user. `billing` references one appointment and uses a generated total. The appointment-to-billing relationship is one-to-zero-or-one through `UNIQUE(appointment_id)`.

## Integrity and performance
Primary keys are auto-increment integers. Foreign keys prevent orphan appointments and bills. Indexes support appointment date, patient, and doctor lookup. Unique keys prevent duplicate usernames, registration numbers, doctor codes, and duplicate bills per appointment. All user-entered SQL values are bound parameters.

## UI modules
LoginFrame provides authentication. PatientPanel provides search, table view, add/edit/delete. DoctorPanel provides specialization/department search and availability display. AppointmentPanel provides booking and status ledger. BillingPanel provides completed-appointment invoice generation and a visit slip dialog.

## Configuration and deployment
Copy `application.properties.example` to `application.properties` under resources. `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables override file values. Build with `mvn clean package`; launch with `mvn exec:java` or run `com.hms.HospitalManagementApplication` from an IDE. The MySQL schema must be applied before login.

## Testing plan
Unit-test `PasswordUtil` and `BillingService.total`. DAO integration tests should run against a test schema and verify authentication, patient CRUD, foreign-key behavior, appointment status transitions, and unique bills. Manual acceptance tests should cover invalid credentials, search, booking, completion, billing, and error dialogs. Performance target for the academic release is sub-second table loading for up to 10,000 rows on a local database, subject to indexes and hardware.

## Security and limitations
Prepared statements and hashed passwords are included. Secrets must not be committed. Production hardening should add salted Argon2/bcrypt password hashes, least-privilege database accounts, TLS, audit logs, input constraints, backups, role-based authorization, and privacy/compliance controls. The current UI is intentionally compact and does not implement doctor CRUD, appointment editing dialog, or printed PDF output; the DAO/service boundaries support those extensions without a schema change.
