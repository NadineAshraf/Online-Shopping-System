public class Clothing extends Product {
	private String size;

	public Clothing(int id, String name, double price, int stock, String size) {
		super(id, name, price, stock);
		this.size = size;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String getDescription() {
		return getName() + " (size: " + size + ")";
	}
}
