# Fashion Store Management System

A Java Swing desktop application developed as an academic team project for managing the core operations of a fashion retail store.

## Features

- Product and inventory management
- Orders and sales workflows
- Promotions
- Customer and employee records
- Supplier management
- Revenue statistics and reporting

## Tech stack

Java · Java Swing · SQL Server · JDBC · Apache Ant / NetBeans

## Structure

The codebase follows a layered desktop-application organization with business logic, data-access objects, data-transfer objects, reusable UI components, screens, utilities, and database integration.

```text
src/
├── BUS/          business logic
├── DAO/          data access
├── DTO/          data transfer objects
├── DTB/          database setup / connection
├── components/   reusable UI components
├── screens/      application views
├── model/        application models
└── utils/        shared utilities
```

## Run locally

Open the project in NetBeans or another Java IDE compatible with the existing Ant project, configure a local SQL Server database, then run the application's main entry point.

Database scripts included in the repository can be used as a starting point for local setup.

## Status

Legacy academic project preserved as part of my software-development history. It is not one of the primary projects highlighted on my current portfolio.


## Local configuration

This legacy project no longer stores database or SMTP credentials in source code.

Configure database and email features through environment variables:

```text
FASHIONSTORE_DB_PASSWORD
FASHIONSTORE_DB_USER
FASHIONSTORE_DB_URL
FASHIONSTORE_SMTP_USER
FASHIONSTORE_SMTP_PASSWORD
FASHIONSTORE_SMTP_HOST
FASHIONSTORE_SMTP_PORT
FASHIONSTORE_DEFAULT_STAFF_PASSWORD
FASHIONSTORE_DEFAULT_ADMIN_PASSWORD
```

Only the password variables are required for the corresponding protected feature; the URL/user/host/port variables have local-development defaults.

Do not commit real credentials, personal contact details, or production customer data to this repository.
