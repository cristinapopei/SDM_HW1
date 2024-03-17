import java.util.List;
import java.util.Set;

public interface CreditCardDAO {
    CreditCard findById(int id);
    Set<CreditCard> findAll();
    List<CreditCard> findByPersonId(int personId);
    CreditCard insert(CreditCard creditCard);
    void update(CreditCard creditCard);
    void delete(int id);
}
