import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    public enum Status {PROCESSING, SHIPPED, DELIVERED}

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private final int orderId;
    private User user;
    private Map<Product, Integer> items = new HashMap<>();
    private double total;
    private Status status = Status.PROCESSING;
    private List<OrderObserver> observers = new ArrayList<>();

    public Order(User user, Map<Product, Integer> items, double total) {
        this.orderId = ID_GENERATOR.getAndIncrement();
        this.user = user;
        this.items.putAll(items);
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        notifyObservers();
    }

    public void addObserver(OrderObserver o) {
        observers.add(o);
    }

    public void removeObserver(OrderObserver o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        for (OrderObserver o : observers) {
            o.update(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + orderId + ", user=" + user.getUsername() + ", total=$" + String.format("%.2f", total) + ", status=" + status + '}';
    }
}

interface OrderObserver {
    void update(Order order);
}
