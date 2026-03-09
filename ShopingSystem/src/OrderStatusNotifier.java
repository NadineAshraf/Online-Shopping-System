public class OrderStatusNotifier {
    // Placeholder for order status notification system
}

class EmailNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println("[EMAIL] Order " + order.getOrderId() + " status: " + order.getStatus());
    }
}

class SMSNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println("[SMS] Order " + order.getOrderId() + " status: " + order.getStatus());
    }
}
