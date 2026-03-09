public class CashOnDelivery implements Payment {
    private String address;

    public CashOnDelivery(String address) {
        this.address = address;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Cash on delivery payment of $" + String.format("%.2f", amount) + " to address " + address);
        return true;
    }
}
