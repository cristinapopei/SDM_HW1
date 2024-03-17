
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JDBCAddressDAOImpl extends COreJDBCDao implements AddressDAO{

    @Override
    public Set<Address> findAll() {
        Set<Address> addresses=new HashSet<>();
        String findAllAddressSQL = "SELECT * FROM addresses";
        try (
                PreparedStatement findAllAddress = connection.prepareStatement(findAllAddressSQL);
        ) {
            ResultSet rs = findAllAddress.executeQuery();
            while (rs.next()){
                Address ad=new Address(rs.getInt("id"),rs.getString("city"),rs.getString("country"));
                addresses.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    public Address insert(Address address) throws SQLException {
        String query = "INSERT INTO addresses (city, country) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, address.getCity());
            statement.setString(2, address.getCountry());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating address failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating address failed, no ID obtained.");
                }
            }
        }
        return address;
    }

    @Override
    public void update(Address address) {
        String updateQuery = "UPDATE addresses SET city = ?, country = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, address.getCity());
            statement.setString(2, address.getCountry());
            statement.setInt(3, address.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int addressId) {
        String deleteQuery = "DELETE FROM addresses WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}