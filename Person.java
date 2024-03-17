import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Person {

    private int id;
    String name;
    LocalDate birthDate;
    String job;
    List<CreditCard> creditCards;

    int addressid;

    public Person(String name, LocalDate birthDate, String job, int addressid) {
        this.name = name;
        this.birthDate = birthDate;
        this.job = job;
        this.addressid= addressid;
        this.creditCards=new ArrayList<>();
    }

    public Person(int id, String name, LocalDate birthDate, String job, int addressid ) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.job = job;
        this.creditCards=new ArrayList<>();
        this.addressid = addressid;
    }

    public String getName() {
        return name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getJob() {
        return job;
    }
    public int getAddressid() {
        return addressid;
    }
    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void addCreditCard(CreditCard creditCard) {
        this.creditCards.add(creditCard);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", job='" + job + '\'' +
                ", creditCards=" + creditCards +
                ", addressid=" + addressid +
                '}';
    }
}
