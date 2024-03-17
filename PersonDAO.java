import java.sql.SQLException;
import java.util.Set;

public interface PersonDAO {
    Set<Person> findAll();
    Person insert(Person person) throws SQLException;
    void update(Person person);
    void delete(int id);
}