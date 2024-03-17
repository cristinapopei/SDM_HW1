
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface AddressDAO {
    Set<Address> findAll();
    Address insert(Address address) throws SQLException;
    void update(Address address);
    void delete(int addressId);
}