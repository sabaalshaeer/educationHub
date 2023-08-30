EducationHub Project

Welcome to the EducationHub project! This comprehensive application establishes a seamless connection between schools, teachers, and students, streamlining data management through well-defined relationships. This README provides a quick overview of the project, its structure, and how to get started.

Features

CRUD Operations: EducationHub supports essential CRUD operations (Create, Read, Update, Delete) for schools, teachers, and students. This ensures efficient data management and seamless user interaction.

Data Relationships: The project employs linked tables to capture the relationships between schools, teachers, and students. These relationships include many-to-many connections between teachers and students, and one-to-many links between schools and teachers, as well as school and students.

Database Configuration: The application is configured to utilize Java Persistence API (JPA) in conjunction with Hibernate, a potent object-relational mapping framework. This combination enables seamless interaction between the Java application and the MySQL database. 
By referring to the application.yaml file, you'll find the necessary database connection details. The provided configuration settings manage aspects such as the database URL, username, password, and Hibernate's behavior. The ddl-auto parameter set to "update" allows Hibernate to automatically create or update the database schema based on your entity classes. Additionally, 
the configuration specifies that SQL initialization should not be performed at application startup, as indicated by the mode set to "never." This tailored setup streamlines the database communication and schema management process for the EducationHub project.
RESTful API: The project provides a RESTful API for easy integration with frontend applications. Endpoints are structured to allow efficient data retrieval and manipulation.

Spring Boot Framework: EducationHub is built using the Spring Boot framework, providing a robust foundation for developing and deploying Java applications.

Getting Started

Prerequisites: Ensure you have Java, Maven, and MySQL installed on your system.

Clone the Repository: Clone this repository to your local machine using git clone https://github.com/sabaalshaeer/EducationHub.git.

Database Setup: Create a MySQL database and update the database connection details in the application.yml configuration file.

Build and Run: Navigate to the project directory and run mvn spring-boot:run to build and start the application.

API Documentation: Simplify your interaction with the API using the Advance Rest Client (ARC) tool. To facilitate your understanding of request types and corresponding endpoints for each operation, we provide a sample.json file within the repository. 
For example, when inserting a school, submit a POST request to /educationhub/school, including the following JSON body:

{
  "schoolName": "Jackson Middle School",
  "schoolAddress": "123 Brooklyn st",
  "schoolCity": "Brooklyn area",
  "schoolState": "MN",
  "schoolZip": "34562",
  "schoolPhone": "543-768-5674"
}


Testing: The EducationHub project utilizes JUnit for unit testing. These tests ensure the reliability and correctness of the application's components. 

Contributing

Contributions to the EducationHub project are welcome! If you find a bug, want to suggest an enhancement, or would like to add a new feature, please submit an issue or create a pull request.
