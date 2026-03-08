# Online-Shopping-System
### **User Features**

1. **User Registration** – Register with username, password, email.
2. **User Login** – Authenticate existing users.
3. **Browse Products** – View list of available products with details (name, price, category, stock).
4. **Search Products** – Filter products by name or category.
5. **Shopping Cart**
    - Add products to cart (with quantity).
    - View cart contents.
    - Update quantity or remove items.
    - Undo last cart action (using a **Stack**).
6. **Place Order**
    - Choose payment method (Credit Card, PayPal, Cash on Delivery).
    - Calculate total amount.
    - After order, cart is cleared and order is added to history.
7. **Order History** – View past orders with status (Processing, Shipped, Delivered).
8. **Logout**
9. ### **Admin Features**
 ### **Suggested Architecture / Class Diagram**
1. **Admin Login** – Separate admin credentials.
2. **Manage Products** – Add new products, update stock, remove products.
3. **View All Orders** – See all orders placed by all users.
4. **Update Order Status** – Change status of orders (e.g., from Processing to Shipped).
5. +----------------+       +------------------+
|     User       |       |    Product (abstract)   |
+----------------+       +------------------+
| -username      |       | -id              |
| -password      |       | -name            |
| -email         |       | -price           |
| -isAdmin       |       | -stock           |
+----------------+       | +getDescription()|
| +getters/setters|       | +applyDiscount()|
+----------------+       +------------------+
         ^                         ^
         |                         |
         |                         | extends
         |              +-----------+-----------+-----------+
         |              |           |           |           |
         |        +----------+ +----------+ +----------+
         |        |Electronics| | Clothing | |   Book   |
         |        +----------+ +----------+ +----------+
         |        |warranty  | |size      | |author    |
         |        +----------+ +----------+ +----------+
         |
+----------------+       +------------------+
|    Cart        |       |     Order        |
+----------------+       +------------------+
| -items: Map<Product, Integer> | -orderId |
| +addItem()     |       | -user            |
| +removeItem()  |       | -items           |
| +updateQty()   |       | -total           |
| +undo()        |       | -status          |
+----------------+       | -paymentMethod   |
                         | -orderDate       |
                         +------------------+

+------------------+       +----------------------+
|   Payment (interface) |       | OrderStatusNotifier (Observer) |
+------------------+       +----------------------+
| +pay(double amount) |       | +update(Order)      |
+------------------+       +----------------------+

+------------------+       +------------------+
| CreditCardPayment|       | PayPalPayment    |
+------------------+       +------------------+
| -cardNumber      |       | -email           |
+------------------+       +------------------+

+------------------+
| CashOnDelivery   |
+------------------+
| -address         |
+------------------+

+-------------------+       +-------------------+
|  ProductFactory   |       | ConfigurationManager (Singleton) |
+-------------------+       +-------------------+
| +createProduct()  |       | -instance         |
+-------------------+       | +getInstance()    |
                            | +getConfig()      |
                            +-------------------+

+-------------------+       +-------------------+
|   UserDAO (interface) |       |  ProductDAO (interface) |
+-------------------+       +-------------------+
| +saveUser()       |       | +saveProduct()    |
| +findUser()       |       | +findProduct()    |
| +getAllUsers()    |       | +getAllProducts() |
+-------------------+       +-------------------+
## **📝 Step-by-Step Implementation Guide / Milestones**

### **Milestone 1: Project Setup & Basic Classes**

1. Create a new Java project in your IDE.
2. Create packages: `model`, `dao`, `service`, `pattern`, `util`, `ui`.
3. Implement `User` class with fields and basic validation in setters.
4. Implement abstract `Product` class and its subclasses.
5. Implement `ConfigurationManager` as Singleton (load basic config like tax rate from a properties file or hardcode).
6. Create a simple in-memory `UserDAO` and `ProductDAO` with `HashMap` to store data.
7. Write a simple console menu in `Main` to test user registration and product listing.

### **Milestone 2: Shopping Cart & Undo Stack**

1. Implement `Cart` class with a `HashMap<Product, Integer>` for items.
2. Add methods: `addProduct`, `removeProduct`, `updateQuantity`, `viewCart`, `getTotal`.
3. Implement undo functionality using a `Stack` of actions (e.g., store previous state as a copy of the cart or store actions as commands).
4. Test cart operations with a simple menu.

### **Milestone 3: Payment Strategy & Order Processing**

1. Define `PaymentStrategy` interface with `pay(double amount)`.
2. Implement concrete payment classes.
3. Create `Order` class with order details, status, and a reference to the payment strategy used.
4. In `Cart`, add `checkout(PaymentStrategy payment)` that creates an `Order`, clears cart, and adds to user's order history.
5. Use a `Queue` to store pending orders (maybe a global order queue for admin processing).

### **Milestone 4: Observer Pattern for Order Status**

1. Create `OrderStatusNotifier` as an observer interface.
2. Implement concrete observers: `EmailNotifier`, `SMSNotifier` (just print messages).
3. In `Order` class, maintain a list of observers, and notify them when status changes.
4. Attach observers when the order is created (or allow users to subscribe).
5. Test by changing order status from admin console.

### **Milestone 5: Admin Features & Data Persistence**

1. Implement admin login (distinguish by `isAdmin` flag in `User`).
2. Admin menu: add product, update stock, remove product, view all orders, update order status.
3. Use DAO interfaces to abstract data storage. For now, keep in-memory, but optionally implement file-based storage using serialization or CSV.
4. Ensure that all data structures (maps, lists) are updated correctly.

### **Milestone 6: Exception Handling & Validation**

1. Add custom exceptions like `ProductNotFoundException`, `InsufficientStockException`, `InvalidPaymentException`.
2. Use try-catch blocks in the UI layer to handle errors gracefully.
3. Validate user input (e.g., non-negative quantities, correct email format).

### **Milestone 7: Final Integration & Testing**

1. Combine all modules in a main driver program with a text-based menu.
2. Test all features: user registration, login, product browsing, cart operations, checkout with different payments, order history, admin functions.
3. Ensure undo stack works.
4. Verify observer notifications on order status change.
