public class CreditCard {

    private int id;
    private String IBAN;
    private double amount;
    private int ownerId;

    public CreditCard(int id, String IBAN, double amount, int ownerId) {
        this.id=id;
        this.IBAN = IBAN;
        this.amount = amount;
        this.ownerId=ownerId;
    }

    public CreditCard(String IBAN, double amount, int ownerId) {
        this.IBAN = IBAN;
        this.amount = amount;
        this.ownerId=ownerId;
    }


    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", IBAN='" + IBAN + '\'' +
                ", amount=" + amount +
                ", ownerId=" + ownerId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }


}
