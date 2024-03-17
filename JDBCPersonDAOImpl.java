import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class JDBCPersonDAOImpl extends COreJDBCDao implements PersonDAO {

    @Override
    public Set<Person> findAll() {
        Set<Person> persons = new HashSet<>();
        String findAllPersonSQL = "SELECT persons.id, persons.name, persons.birthdate, persons.job, addresses.city, addresses.country " +
                "FROM persons " +
                "JOIN addresses ON persons.address_id = addresses.id";
        try (
                PreparedStatement findAllPerson = connection.prepareStatement(findAllPersonSQL);
        ) {
            ResultSet rs = findAllPerson.executeQuery();
            while (rs.next()) {
                Address address = new Address(rs.getString("city"), rs.getString("country"));
                Person person = new Person(rs.getString("name"), rs.getObject("birthdate", LocalDate.class), rs.getString("job"), rs.getInt("address_id"));
                person.setId(rs.getInt("id")); // Set person's ID
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person insert(Person person) throws SQLException {
        String query = "INSERT INTO persons (name, birthDate, job, address_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getName());
            statement.setDate(2, Date.valueOf(person.getBirthDate()));
            statement.setString(3, person.getJob());
            statement.setInt(4, person.getAddressid());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating person failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }
        }
        return person;
    }


    @Override
    public void update(Person person) {
        String updateQuery = "UPDATE persons SET name = ?, birthDate = ?, job = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, person.getName());
            statement.setDate(2, java.sql.Date.valueOf(person.getBirthDate()));
            statement.setString(3, person.getJob());
            statement.setInt(4, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteQuery = "DELETE FROM persons WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
