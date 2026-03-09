package model;

import java.util.List;

public class order {

    private static int idCounter = 1;
    private int orderId;
    private List<String> items;
    private double totalAmount;
    private String status; // Processing, Shipped, Delivered
    private PaymentStrategy paymentMethod;

    public Order(List<String> items, double totalAmount, PaymentStrategy paymentMethod) {
        this.orderId = idCounter++;
        this.items = List.copyOf(items);
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = "Processing";
    }

    public void processOrder() {
        paymentMethod.pay(totalAmount);
        System.out.println("Order #" + orderId + " is now being processed.");
    }

}


