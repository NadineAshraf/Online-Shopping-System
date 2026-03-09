import java.util.Objects;

public abstract class Product {
	private final int id;
	private String name;
	private double price;
	private int stock;

	public Product(int id, String name, double price, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void reduceStock(int amount) {
		if (amount <= stock) {
			stock -= amount;
		}
	}

	public abstract String getDescription();

	@Override
	public String toString() {
		return "[" + id + "] " + name + " - " + price + " (stock: " + stock + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return id == product.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
