# Visual File Directory Map

```text
HospitalManagementSystem/
├── pom.xml
├── README.md
├── .gitignore
├── db/
│   └── schema.sql
├── docs/
│   ├── DIRECTORY_TREE.md
│   ├── PRD.md
│   ├── TRD.md
│   └── WORKFLOW.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── hms/
        │           ├── HospitalManagementApplication.java
        │           ├── config/
        │           │   ├── DatabaseConfig.java
        │           │   └── DatabaseConnection.java
        │           ├── dao/
        │           │   ├── AppointmentDao.java
        │           │   ├── BillingDao.java
        │           │   ├── DoctorDao.java
        │           │   ├── PatientDao.java
        │           │   ├── UserDao.java
        │           │   └── impl/
        │           │       ├── AppointmentDaoImpl.java
        │           │       ├── BillingDaoImpl.java
        │           │       ├── DoctorDaoImpl.java
        │           │       ├── PatientDaoImpl.java
        │           │       └── UserDaoImpl.java
        │           ├── model/
        │           │   ├── Appointment.java
        │           │   ├── Billing.java
        │           │   ├── Doctor.java
        │           │   ├── Patient.java
        │           │   └── User.java
        │           ├── service/
        │           │   ├── AppointmentService.java
        │           │   ├── AuthService.java
        │           │   ├── BillingService.java
        │           │   ├── DoctorService.java
        │           │   └── PatientService.java
        │           ├── ui/
        │           │   ├── AppointmentPanel.java
        │           │   ├── BillingPanel.java
        │           │   ├── DoctorPanel.java
        │           │   ├── LoginFrame.java
        │           │   ├── MainFrame.java
        │           │   └── PatientPanel.java
        │           └── util/
        │               ├── PasswordUtil.java
        │               └── UiUtil.java
        └── resources/
            └── application.properties.example
```

All Java files are complete source files; there are no generated GUI-builder dependencies or placeholder classes.