public class PayPalPayment implements Payment {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Processing PayPal payment of $" + String.format("%.2f", amount) + " with email " + email);
        return true;
    }
}
