import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JDBCCreditCardDAOImpl extends COreJDBCDao implements CreditCardDAO {

    public Set<CreditCard> findAll() {
        Set<CreditCard> creditCards = new HashSet<>();
        String findAllCreditCardsSQL = "SELECT * FROM creditcard";
        try (
                PreparedStatement findAllCreditCards = connection.prepareStatement(findAllCreditCardsSQL);
        ) {
            ResultSet rs = findAllCreditCards.executeQuery();
            while (rs.next()) {
                CreditCard creditCard = new CreditCard(rs.getInt("id"), rs.getString("IBAN"), rs.getDouble("amount"), rs.getInt("owner"));
                creditCards.add(creditCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditCards;
    }
    @Override
    public CreditCard findById(int id) {
        String query = "SELECT * FROM creditcard WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractCreditCardFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CreditCard> findByPersonId(int personId) {
        List<CreditCard> creditCards = new ArrayList<>();
        String query = "SELECT * FROM creditcard WHERE owner = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditCards.add(extractCreditCardFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creditCards;
    }

    @Override
    public CreditCard insert(CreditCard creditCard) {
        String query = "INSERT INTO creditcard (IBAN, amount, owner) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, creditCard.getIBAN());
            statement.setDouble(2, creditCard.getAmount());
            statement.setInt(3, creditCard.getOwnerId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting credit card failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                creditCard.setId(generatedKeys.getInt(1));
                return creditCard;
            } else {
                throw new SQLException("Inserting credit card failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(CreditCard creditCard) {
        String query = "UPDATE creditcard SET IBAN = ?, amount = ?, owner= ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, creditCard.getIBAN());
            statement.setDouble(2, creditCard.getAmount());
            statement.setInt(3, creditCard.getOwnerId());
            statement.setInt(4, creditCard.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM creditcard WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CreditCard extractCreditCardFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String iban = resultSet.getString("IBAN");
        double amount = resultSet.getDouble("amount");
        int personId = resultSet.getInt("person_id");
        return new CreditCard(id, iban, amount, personId);
    }

}
