# Complete Project Workflow and Exact Logic

## 1. Startup and authentication

`HospitalManagementApplication` starts the Swing event-dispatch thread and opens `LoginFrame`. The user enters a username and password. `AuthService` validates non-empty input, hashes the password using SHA-256, and calls `UserDaoImpl.authenticate`. The DAO uses a parameterized query against active users. If a matching record exists, `MainFrame` is opened with the authenticated user identity; otherwise a failure message is shown.

## 2. Patient records

The Patients tab loads all rows using a case-insensitive-style SQL `LIKE` search across registration number, name, and phone. Add and edit dialogs validate the date through `LocalDate.parse`. `PatientDaoImpl` uses prepared INSERT/UPDATE statements. Delete is confirmed in the UI and is intentionally subject to foreign-key protection if appointments exist. The JTable is refreshed after every successful write.

## 3. Doctor lookup

The Doctors tab displays active doctors. Search matches doctor code, name, specialization, and department. Availability is displayed from the database fields `available_days`, `available_from`, and `available_to`; it is used by the receptionist during appointment selection.

## 4. Appointment booking and status

The appointment dialog loads patients and active doctors from their DAOs, captures date/time/reason, and inserts a row with status `Scheduled`. The ledger joins appointments to patient and doctor names and orders by date/time. The status actions update the row to `Completed` or `Cancelled`. Delete is available at the DAO layer for future UI extension. Rescheduling is implemented by editing the appointment date/time through the same service boundary; a production UI can expose an edit dialog without changing the schema.

## 5. Billing and visit slip

Billing is restricted to completed appointments. The receptionist enters consultation fee, service fee, discount, tax, payment status, and paid amount. The database generated column calculates `total_amount = consultation_fee + service_fee + tax - discount`, while the UI calculates the same value for the in-memory slip. A bill is unique per appointment. The display action produces a monospaced visit slip dialog suitable for printing/copying.

## 6. Error and transaction behavior

Each DAO opens and closes its own JDBC connection with try-with-resources. SQL errors propagate to the UI, where `UiUtil.showError` reports the operation. Parameterized statements prevent SQL injection. Foreign keys preserve referential integrity between patients, doctors, appointments, and billing.
