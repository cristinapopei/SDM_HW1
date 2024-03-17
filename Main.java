import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /// address test
        /*
        JDBCAddressDAOImpl addressDao=new JDBCAddressDAOImpl();
        Set<Address> addresses=addressDao.findAll();
        addresses.stream().forEach(System.out::println);
        //Address newAddress = new Address("Annecy","Franta");
        //addressDao.insert(newAddress);
        System.out.println("==============================");
        addresses=addressDao.findAll();
        addresses.stream().forEach(System.out::println);
        addressDao.closeConnection();

        /// person test
        JDBCPersonDAOImpl personDao = new JDBCPersonDAOImpl();
        Set<Person> persons = personDao.findAll();
        System.out.println("Persons:");
        persons.forEach(System.out::println);
        //Person newPerson = new Person("Ion", LocalDate.of(1990, 5, 15), "Software Engineer", newAddress);
        //personDao.insert(newPerson);
        System.out.println("==============================");

        persons = personDao.findAll();
        System.out.println("Updated Persons:");
        persons.forEach(System.out::println);
        personDao.closeConnection();

        /// credit card test
        JDBCCreditCardDAOImpl creditCardDAO = new JDBCCreditCardDAOImpl();
       // CreditCard creditCard1 = new CreditCard("1234567890123456", 1000.0, 10);
       // CreditCard creditCard2 = new CreditCard("9876543210987654", 2000.0, 11);
       // creditCardDAO.insert(creditCard1);
       // creditCardDAO.insert(creditCard2);

        Set<CreditCard> creditCards = creditCardDAO.findAll();
        System.out.println("Credit Cards:");
        for (CreditCard card : creditCards) {
            System.out.println(card);
        }
        creditCardDAO.closeConnection();
        */

        ///  homework task 3 & 4

        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/hw1", "root", "root");

        List<Person> people = fetchPeopleFromDatabase(connection);

        System.out.println("Fetched People:");
        for (Person person : people) {
            System.out.println(person);
        }

        // we allow the user to insert a new Person object

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the new person:");
        String name = scanner.nextLine();
        System.out.println("Enter the birth date (YYYY-MM-DD) of the new person:");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);  // we have to parse the string input into a LocalDate object
        System.out.println("Enter the job of the new person:");
        String job = scanner.nextLine();
        System.out.println("Enter the city of the new address:");
        String city = scanner.nextLine();
        System.out.println("Enter the country of the new address:");
        String country = scanner.nextLine();
        System.out.println("Enter the IBAN of the new credit card:");
        String IBAN = scanner.nextLine();
        System.out.println("Enter the amount of the new credit card:");
        double amount = Double.parseDouble(scanner.nextLine());

        //we create a new Address object
        Address newAddress = new Address(city, country);
        JDBCAddressDAOImpl addressDAO = new JDBCAddressDAOImpl();
        Address insertedAddress = addressDAO.insert(newAddress);

        //we  create a new Person object with the retrieved address ID
        Person newPerson = new Person(name, date, job, insertedAddress.getId());
        JDBCPersonDAOImpl personDAO = new JDBCPersonDAOImpl();
        Person insertedPerson = personDAO.insert(newPerson);

        // we create a new CreditCard object associated with the inserted person
        JDBCCreditCardDAOImpl creditCardDAO = new JDBCCreditCardDAOImpl();
        CreditCard newCreditCard = new CreditCard(IBAN, amount, newPerson.getId());
        CreditCard insertedCreditCard = creditCardDAO.insert(newCreditCard);

        List<Person> people1 = fetchPeopleFromDatabase(connection);

        System.out.println("Fetched People UPDATED:");
        for (Person person : people1) {
            System.out.println(person);
        }

        connection.close();
        scanner.close();
    }

    private static List<Person> fetchPeopleFromDatabase(Connection connection) throws SQLException {
        List<Person> people = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.birthDate, p.job, a.id AS address_id, a.city, a.country, c.id AS creditcard_id, c.IBAN, c.amount " +
                "FROM persons p " +
                "JOIN addresses a ON p.address_id = a.id " +
                "JOIN creditcard c ON p.id = c.owner";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("birthDate").toLocalDate();
                String job = resultSet.getString("job");
                int addressId = resultSet.getInt("address_id");
                String city = resultSet.getString("city");
                String country = resultSet.getString("country");

                Address address = new Address(addressId, city, country);

                // Check if the person already exists in the list
                Person person = findPersonById(people, id);
                if (person == null) {
                    // if the person doesn't exist,we create a new person object and add it to the list
                    person = new Person(id, name, date, job, addressId);
                    person.setAddressid(address.getId());
                    people.add(person);
                }

                int ccId = resultSet.getInt("creditcard_id");
                String IBAN = resultSet.getString("IBAN");
                double amount = resultSet.getDouble("amount");

                //wecreate a credit card object and associate it with the person
                CreditCard creditCard = new CreditCard(ccId, IBAN, amount, id);
                person.getCreditCards().add(creditCard);

            }
        }
        return people;
    }

    private static Person findPersonById(List<Person> people, int id) {
        for (Person personn : people) {
            if (personn.getId() == id) {
                return personn;
            }
        }
        return null;
    }

}
