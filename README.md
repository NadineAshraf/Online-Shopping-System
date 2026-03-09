# Online Shopping System - Java Comprehensive Project

A fully functional **Online Shopping System** console application demonstrating core Java concepts, OOP principles, data structures, and design patterns.

---

## ✅ All Milestones Completed

This project **fully implements all 7 milestones** with comprehensive feature coverage, proper design patterns, and robust error handling.

### **Milestone 1: Project Setup & Basic Classes** ✓
- ✓ Java project structure set up
- ✓ `User` class with fields, getters/setters, and order history tracking
- ✓ Abstract `Product` class with concrete subclasses:
  - `Book` (with author field)
  - `Clothing` (with size field)  
  - `Electronics` (with warranty months)
- ✓ `ConfigurationManager` Singleton with tax rate (8%) and currency (USD)
- ✓ `UserDAO` and `ProductDAO` interfaces with HashMap-based in-memory implementations
- ✓ Console menu system for user registration, login, and product listing

### **Milestone 2: Shopping Cart & Undo Stack** ✓
- ✓ `Cart` class with HashMap<Product, Integer> for item management
- ✓ Complete method set:
  - `addProduct()` - Add items with quantity
  - `removeProduct()` - Remove items completely
  - `updateQuantity()` - Modify item quantities
  - `getTotal()` - Calculate cart subtotal
  - `undo()` - Revert last action
- ✓ **Stack-based undo** storing deep copies of cart state
- ✓ Menu option for testing cart operations

### **Milestone 3: Payment Strategy & Order Processing** ✓
- ✓ `Payment` interface with `pay(double amount)` contract
- ✓ Three concrete payment strategies:
  - `CreditCardPayment` - Card number, expiry, CVV fields
  - `PayPalPayment` - Email-based payment
  - `CashOnDelivery` - Address-based payment
- ✓ `Order` class with:
  - Unique order ID via AtomicInteger
  - Status enum (PROCESSING, SHIPPED, DELIVERED)
  - Observer list for notifications
  - Item and total tracking
- ✓ `Cart.checkout()` method that:
  - Processes payment via strategy
  - Creates Order with user and items
  - Adds order to user history
  - Clears cart automatically
- ✓ `Queue<Order>` for pending order processing

### **Milestone 4: Observer Pattern for Order Status** ✓
- ✓ `OrderObserver` interface defined in Order.java
- ✓ Concrete observer implementations:
  - `EmailNotifier` - Prints email notifications
  - `SMSNotifier` - Prints SMS notifications
- ✓ Order maintains observer list with:
  - `addObserver()` method
  - `removeObserver()` method
  - Auto-notification on status change
- ✓ Observers automatically attached during checkout
- ✓ Status updates trigger notifications

### **Milestone 5: Admin Features & Data Persistence** ✓
- ✓ Admin login via `isAdmin` boolean flag
- ✓ Full admin menu:
  - Add products (supporting book, clothing, electronics)
  - Update product stock
  - Remove products
  - View all orders globally
  - Update order status with auto-notifications
- ✓ DAO interfaces abstract data storage (ready for file-based upgrade)
- ✓ In-memory HashMap implementations maintain data consistency

### **Milestone 6: Exception Handling & Validation** ✓
- ✓ Custom exceptions:
  - `ProductNotFoundException` - Product lookup failures
  - `InsufficientStockException` - Stock validation
  - `InvalidPaymentException` - Payment method errors
- ✓ Try-catch blocks in UI layer for graceful error handling
- ✓ Input validation:
  - Email format validation with regex
  - Quantity validation (positive values)
  - Stock availability checks
  - Proper exception propagation
- ✓ User-friendly error messages

### **Milestone 7: Final Integration & Testing** ✓
- ✓ Complete Main driver with unified menu system
- ✓ User login/registration menu
- ✓ User menu with 10 options for full cart operations
- ✓ Admin menu with 6 options for product/order management
- ✓ All features work end-to-end:
  - Registration with email validation
  - Product browsing and search
  - Cart management with undo
  - Multi-method checkout
  - Order history viewing
  - Admin inventory management
  - Order status updates with notifications
- ✓ Stack-based undo verified
- ✓ Observer pattern working via order status changes

---

## 🏗 Architecture

### **Design Patterns Used**

| Pattern | Class | Purpose |
|---------|-------|---------|
| **Singleton** | ConfigurationManager | Single global configuration |
| **Factory** | ProductFactory | Dynamic product creation |
| **Strategy** | Payment interface + 3 implementations | Flexible payment processing |
| **Observer** | OrderObserver + EmailNotifier/SMSNotifier | Order notifications |
| **DAO** | UserDAO/ProductDAO interfaces | Data access abstraction |

### **Data Structures**

- **HashMap** - User/product storage, cart items
- **ArrayList** - Product lists, order history
- **Stack** - Cart undo history
- **Queue** - Pending orders
- **AtomicInteger** - Thread-safe order ID generation

---

## 📋 How to Compile & Run

### **Compile**
```bash
cd ShopingSystem
javac src/*.java
```

### **Execute**
```bash
java -cp src Main
```

### **Test Credentials**
- **Admin**: Username: `admin` | Password: `admin123`
- **New User**: Register via menu option 2

---

## 🎯 Features

### **User Menu** (10 Options)
1. Browse Products - View all items with descriptions
2. Search Products - Filter by name
3. View Cart - See items and total
4. Add to Cart - Select product and quantity
5. Remove from Cart - Remove item
6. Update Cart Quantity - Change quantities
7. Undo Last Action - Revert changes
8. Checkout - Process payment
9. View Order History - See past orders
10. Logout

### **Admin Menu** (6 Options)
1. Add Product - Create new items
2. Update Stock - Modify inventory
3. Remove Product - Delete items
4. View All Orders - See all orders
5. Update Order Status - Change status (triggers notifications)
6. Logout

---

## ✨ Key Highlights

- **Robust Error Handling**: Custom exceptions + graceful error messages
- **Input Validation**: Email regex, quantity checks, stock validation
- **Design Patterns**: 5 major patterns properly implemented
- **Data Structures**: Efficient collections for each use case
- **OOP Principles**: Full encapsulation, inheritance, polymorphism, abstraction
- **Clean Code**: Consistent style, meaningful names, clear organization

---

## 📁 Source Files (20 Java Files)

**Core Models:**
- User.java, Product.java, Cart.java, Order.java

**Product Subclasses:**
- Book.java, Clothing.java, Electronics.java

**Payment Strategy:**
- Payment.java, CreditCardPayment.java, PayPalPayment.java, CashOnDelivery.java

**Data Access:**
- UserDAO.java, ProductDAO.java

**Observers:**
- OrderStatusNotifier.java, (EmailNotifier, SMSNotifier inside)

**Design Patterns:**
- ConfigurationManager.java, ProductFactory.java

**Exceptions:**
- ProductNotFoundException.java, InsufficientStockException.java, InvalidPaymentException.java

**Main Application:**
- Main.java

---

**Status**: ✅ **100% Complete** - All milestones implemented and tested  
**Version**: 1.0  
**Java Version**: Compatible with Java 8+  
**Last Updated**: March 2026
