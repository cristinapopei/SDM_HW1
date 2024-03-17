Project Name: Database Integration with Java

Description:
This project demonstrates how to integrate a Java application with a relational database using JDBC (Java Database Connectivity). It includes functionalities to fetch data from the database, insert new records, and display the retrieved data.

Components:

    Main.java: Contains the main method to run the application. It includes methods to fetch people from the database, insert new people, and display the fetched data.
    Person.java: Represents a person entity with attributes such as ID, name, birth date, job, and address ID.
    Address.java: Represents an address entity with attributes such as ID, city, and country.
    CreditCard.java: Represents a credit card entity with attributes such as ID, IBAN, amount, and owner ID.
    JDBCPersonDAOImpl.java: Implements the data access object (DAO) for the person entity, providing methods to insert, fetch, and manipulate person records in the database.
    JDBCAddressDAOImpl.java: Implements the DAO for the address entity, providing methods to insert, fetch, and manipulate address records in the database.
    JDBCCreditCardDAOImpl.java: Implements the DAO for the credit card entity, providing methods to insert, fetch, and manipulate credit card records in the database.

Instructions:

    Ensure you have MariaDB installed and running.
    Set up the database schema and tables using the provided SQL scripts.
    Update the database connection parameters in the Java application (Main.java) to match your database configuration.
    Compile and run the Main.java file to execute the application.

Functionality:

    Fetch people from the database along with their associated address and credit card information.
    Insert new people into the database along with their address and credit card details.
    Display the fetched data in a structured format, showing each person's attributes and associated credit cards.

Dependencies:

    MariaDB Connector/J: JDBC driver for MariaDB.
    Java SE Development Kit (JDK): Required to compile and run the Java application.
