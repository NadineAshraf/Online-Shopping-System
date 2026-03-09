import java.util.*;
import java.util.regex.Pattern;

public class Main {
    private static final UserDAO userDAO = createUserDAO();
    private static final ProductDAO productDAO = createProductDAO();
    private static final Queue<Order> orderQueue = new LinkedList<>();
    private static User currentUser = null;
    private static Cart cart = new Cart();

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            try {
                if (currentUser == null) {
                    running = showLoginMenu(scanner);
                } else if (currentUser.isAdmin()) {
                    showAdminMenu(scanner);
                } else {
                    showUserMenu(scanner);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static UserDAO createUserDAO() {
        return new UserDAO() {
            private Map<String, User> users = new HashMap<>();
            @Override
            public void saveUser(User user) { users.put(user.getUsername(), user); }
            @Override
            public User findUser(String username) { return users.get(username); }
            @Override
            public List<User> getAllUsers() { return new ArrayList<>(users.values()); }
            @Override
            public void updateUser(User user) { users.put(user.getUsername(), user); }
            @Override
            public void deleteUser(String username) { users.remove(username); }
        };
    }

    private static ProductDAO createProductDAO() {
        return new ProductDAO() {
            private Map<Integer, Product> products = new HashMap<>();
            @Override
            public void saveProduct(Product product) { products.put(product.getId(), product); }
            @Override
            public Product findProduct(int id) { return products.get(id); }
            @Override
            public List<Product> getAllProducts() { return new ArrayList<>(products.values()); }
            @Override
            public void updateProduct(Product product) { products.put(product.getId(), product); }
            @Override
            public void deleteProduct(int id) { products.remove(id); }
        };
    }

    private static void initializeData() {
        // Sample products
        productDAO.saveProduct(new Book(1, "Java Programming", 29.99, 10, "John Doe"));
        productDAO.saveProduct(new Clothing(2, "T-Shirt", 19.99, 20, "M"));
        productDAO.saveProduct(new Electronics(3, "Laptop", 999.99, 5, 24));
        productDAO.saveProduct(new Book(4, "Python Guide", 35.99, 8, "Jane Smith"));

        // Admin user
        userDAO.saveUser(new User("admin", "admin123", "admin@example.com", true));
    }

    private static boolean showLoginMenu(Scanner scanner) {
        System.out.println("\n========== LOGIN MENU ==========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose: ");
        
        int choice = getIntInput(scanner);
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                login(scanner);
                break;
            case 2:
                register(scanner);
                break;
            case 3:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid choice");
        }
        return true;
    }

    private static void login(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = userDAO.findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            cart = new Cart();
            System.out.println("✓ Logged in as " + username);
        } else {
            System.out.println("✗ Invalid credentials");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        if (userDAO.findUser(username) != null) {
            System.out.println("✗ Username already exists");
            return;
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        if (!isValidEmail(email)) {
            System.out.println("✗ Invalid email format");
            return;
        }
        
        User user = new User(username, password, email, false);
        userDAO.saveUser(user);
        System.out.println("✓ Registered successfully");
    }

    private static void showUserMenu(Scanner scanner) {
        System.out.println("\n========== USER MENU ==========");
        System.out.println("1. Browse Products");
        System.out.println("2. Search Products");
        System.out.println("3. View Cart");
        System.out.println("4. Add to Cart");
        System.out.println("5. Remove from Cart");
        System.out.println("6. Update Cart Quantity");
        System.out.println("7. Undo Last Cart Action");
        System.out.println("8. Checkout");
        System.out.println("9. View Order History");
        System.out.println("10. Logout");
        System.out.print("Choose: ");
        
        int choice = getIntInput(scanner);
        scanner.nextLine();
        
        try {
            switch (choice) {
                case 1:
                    browseProducts();
                    break;
                case 2:
                    searchProducts(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    addToCart(scanner);
                    break;
                case 5:
                    removeFromCart(scanner);
                    break;
                case 6:
                    updateCartQuantity(scanner);
                    break;
                case 7:
                    cart.undo();
                    System.out.println("✓ Undid last action");
                    break;
                case 8:
                    checkout(scanner);
                    break;
                case 9:
                    viewOrderHistory();
                    break;
                case 10:
                    currentUser = null;
                    System.out.println("✓ Logged out");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void browseProducts() throws ProductNotFoundException {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products available");
        }
        System.out.println("\n========== PRODUCTS ==========");
        for (Product p : products) {
            System.out.println(p + " | " + p.getDescription());
        }
    }

    private static void searchProducts(Scanner scanner) {
        System.out.print("Search term: ");
        String term = scanner.nextLine();
        System.out.println("\n========== SEARCH RESULTS ==========");
        List<Product> products = productDAO.getAllProducts();
        boolean found = false;
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(term.toLowerCase())) {
                System.out.println(p + " | " + p.getDescription());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No products found");
        }
    }

    private static void viewCart() {
        Map<Product, Integer> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        System.out.println("\n========== YOUR CART ==========");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            double lineTotal = e.getKey().getPrice() * e.getValue();
            System.out.println(e.getKey().getName() + " x" + e.getValue() + " = $" + String.format("%.2f", lineTotal));
        }
        System.out.println("Total: $" + String.format("%.2f", cart.getTotal()));
    }

    private static void addToCart(Scanner scanner) throws InsufficientStockException, ProductNotFoundException {
        System.out.print("Product ID: ");
        int id = getIntInput(scanner);
        System.out.print("Quantity: ");
        int qty = getIntInput(scanner);
        scanner.nextLine();
        
        if (qty <= 0) {
            throw new InsufficientStockException("Quantity must be positive");
        }
        
        Product p = productDAO.findProduct(id);
        if (p == null) {
            throw new ProductNotFoundException("Product ID " + id + " not found");
        }
        
        if (p.getStock() < qty) {
            throw new InsufficientStockException("Only " + p.getStock() + " items in stock");
        }
        
        cart.addProduct(p, qty);
        p.reduceStock(qty);
        System.out.println("✓ Added " + p.getName() + " to cart");
    }

    private static void removeFromCart(Scanner scanner) throws ProductNotFoundException {
        System.out.print("Product ID: ");
        int id = getIntInput(scanner);
        scanner.nextLine();
        
        Product p = productDAO.findProduct(id);
        if (p == null) {
            throw new ProductNotFoundException("Product ID " + id + " not found");
        }
        
        cart.removeProduct(p);
        System.out.println("✓ Removed from cart");
    }

    private static void updateCartQuantity(Scanner scanner) throws InsufficientStockException {
        System.out.print("Product ID: ");
        int id = getIntInput(scanner);
        System.out.print("New Quantity: ");
        int qty = getIntInput(scanner);
        scanner.nextLine();
        
        if (qty < 0) {
            throw new InsufficientStockException("Quantity cannot be negative");
        }
        
        Product p = productDAO.findProduct(id);
        if (p != null) {
            cart.updateQuantity(p, qty);
            System.out.println("✓ Updated quantity");
        }
    }

    private static void checkout(Scanner scanner) throws InvalidPaymentException {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        
        System.out.println("\n========== PAYMENT METHOD ==========");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.println("3. Cash on Delivery");
        System.out.print("Choose: ");
        
        int choice = getIntInput(scanner);
        scanner.nextLine();
        
        Payment payment = null;
        try {
            switch (choice) {
                case 1:
                    System.out.print("Card Number: ");
                    String card = scanner.nextLine();
                    payment = new CreditCardPayment(card, "12/25", "123");
                    break;
                case 2:
                    System.out.print("PayPal Email: ");
                    String email = scanner.nextLine();
                    payment = new PayPalPayment(email);
                    break;
                case 3:
                    System.out.print("Delivery Address: ");
                    String address = scanner.nextLine();
                    payment = new CashOnDelivery(address);
                    break;
                default:
                    throw new InvalidPaymentException("Invalid payment method");
            }
            
            if (payment != null) {
                System.out.println("Processing payment of $" + String.format("%.2f", cart.getTotal()) + "...");
                Order order = cart.checkout(currentUser, payment);
                if (order != null) {
                    order.addObserver(new EmailNotifier());
                    order.addObserver(new SMSNotifier());
                    orderQueue.add(order);
                    System.out.println("✓ Order placed successfully!");
                    System.out.println(order);
                } else {
                    throw new InvalidPaymentException("Payment failed");
                }
            }
        } catch (InvalidPaymentException e) {
            throw e;
        }
    }

    private static void viewOrderHistory() {
        List<Order> orders = currentUser.getOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("No orders yet");
            return;
        }
        System.out.println("\n========== ORDER HISTORY ==========");
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private static void showAdminMenu(Scanner scanner) {
        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. Add Product");
        System.out.println("2. Update Stock");
        System.out.println("3. Remove Product");
        System.out.println("4. View All Orders");
        System.out.println("5. Update Order Status");
        System.out.println("6. Logout");
        System.out.print("Choose: ");
        
        int choice = getIntInput(scanner);
        scanner.nextLine();
        
        try {
            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateStock(scanner);
                    break;
                case 3:
                    removeProduct(scanner);
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 5:
                    updateOrderStatus(scanner);
                    break;
                case 6:
                    currentUser = null;
                    System.out.println("✓ Logged out");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Type (book/clothing/electronics): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("ID: ");
        int id = getIntInput(scanner);
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = getDoubleInput(scanner);
        System.out.print("Stock: ");
        int stock = getIntInput(scanner);
        scanner.nextLine();
        
        Product p = null;
        try {
            switch (type) {
                case "book":
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    p = new Book(id, name, price, stock, author);
                    break;
                case "clothing":
                    System.out.print("Size: ");
                    String size = scanner.nextLine();
                    p = new Clothing(id, name, price, stock, size);
                    break;
                case "electronics":
                    System.out.print("Warranty (months): ");
                    int warranty = getIntInput(scanner);
                    scanner.nextLine();
                    p = new Electronics(id, name, price, stock, warranty);
                    break;
                default:
                    System.out.println("Invalid type");
                    return;
            }
            if (p != null) {
                productDAO.saveProduct(p);
                System.out.println("✓ Product added");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStock(Scanner scanner) throws ProductNotFoundException {
        System.out.print("Product ID: ");
        int id = getIntInput(scanner);
        System.out.print("New Stock: ");
        int stock = getIntInput(scanner);
        scanner.nextLine();
        
        Product p = productDAO.findProduct(id);
        if (p == null) {
            throw new ProductNotFoundException("Product ID " + id + " not found");
        }
        p.setStock(stock);
        productDAO.updateProduct(p);
        System.out.println("✓ Stock updated");
    }

    private static void removeProduct(Scanner scanner) throws ProductNotFoundException {
        System.out.print("Product ID: ");
        int id = getIntInput(scanner);
        scanner.nextLine();
        
        Product p = productDAO.findProduct(id);
        if (p == null) {
            throw new ProductNotFoundException("Product ID " + id + " not found");
        }
        productDAO.deleteProduct(id);
        System.out.println("✓ Product removed");
    }

    private static void viewAllOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders");
            return;
        }
        System.out.println("\n========== ALL ORDERS ==========");
        for (Order o : orderQueue) {
            System.out.println(o);
        }
    }

    private static void updateOrderStatus(Scanner scanner) throws ProductNotFoundException {
        System.out.print("Order ID: ");
        int id = getIntInput(scanner);
        System.out.print("New Status (PROCESSING/SHIPPED/DELIVERED): ");
        String statusStr = scanner.nextLine().toUpperCase();
        
        try {
            Order.Status status = Order.Status.valueOf(statusStr);
            for (Order o : orderQueue) {
                if (o.getOrderId() == id) {
                    o.setStatus(status);
                    System.out.println("✓ Order status updated to " + status);
                    return;
                }
            }
            throw new ProductNotFoundException("Order ID " + id + " not found");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status");
        }
    }

    private static int getIntInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static double getDoubleInput(Scanner scanner) {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1.0;
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }
}
