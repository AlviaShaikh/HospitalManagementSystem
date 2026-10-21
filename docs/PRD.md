# Product Requirements Document (Academic Synopsis)

## Title
Hospital Management System

## Abstract
The system is a desktop application for receptionists and administrators to maintain patient records, doctor availability, appointments, and outpatient billing from one Swing interface backed by MySQL. It reduces paper-based registration errors and provides a searchable appointment ledger and visit slip.

## Problem statement
Small outpatient clinics need a low-cost, locally deployable application for registering patients, finding doctors by specialization, booking visits, tracking appointment status, and issuing a basic invoice. Spreadsheet or paper workflows make duplicate records and missed status updates likely.

## Objectives
1. Authenticate authorized reception and administrative staff.
2. Perform CRUD operations on patient profiles.
3. Provide doctor department, specialization, and availability lookup.
4. Schedule appointments linked to stable patient and doctor IDs.
5. Track Scheduled, Completed, and Cancelled appointments.
6. Calculate and display outpatient invoices.
7. Preserve data integrity using MySQL foreign keys.

## Scope
In scope: user authentication, patient registration/records, doctor lookup, appointment booking/status, billing/visit slip display, SQL seed data, and JDBC persistence. Out of scope: inpatient wards, pharmacy, laboratory, insurance claims, online payments, SMS/email, multi-branch deployment, and clinical decision support.

## Stakeholders and users
Receptionists register patients and manage appointments. Administrators use all reception functions and oversee master data. Doctors are represented as profiles but do not log in to this academic release. The clinic manager receives operational visibility through the appointment and billing tables.

## Functional requirements
FR-01: Verify username/password against active MySQL user records.
FR-02: Add, update, search, view, and delete patient profiles.
FR-03: Search doctors by name, code, specialization, and department.
FR-04: Record doctor working days and working hours.
FR-05: Book appointments using patient and doctor foreign keys.
FR-06: Show upcoming and historical appointments in a JTable ledger.
FR-07: Update appointment status to Scheduled, Completed, or Cancelled.
FR-08: Generate a bill for a completed appointment.
FR-09: Calculate total as consultation + service + tax - discount.
FR-10: Display a visit slip with bill, patient, doctor, and payment details.

## Non-functional requirements
The interface shall be usable with keyboard and mouse. The app shall use prepared statements, close JDBC resources, and provide meaningful error dialogs. The code shall be modular enough to test DAOs independently. The database shall enforce referential integrity. Initial target is a single clinic workstation or trusted LAN deployment.

## Acceptance criteria
A seeded installation launches, accepts the documented credentials, lists seed data, permits a new patient, books an appointment, changes it to Completed, generates a bill, and displays the visit slip. Invalid login is rejected. Search filters result rows. Deleting a patient with dependent appointments is blocked by database integrity rather than silently deleting history.

## Assumptions and risks
The workstation can reach MySQL and has the Connector/J dependency. Dates and times use the database/server timezone. SHA-256 is acceptable only for this educational synopsis; production deployment requires a salted adaptive password algorithm and formal access control/audit/privacy review.
