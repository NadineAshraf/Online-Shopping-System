public class Electronics extends Product {
	private int warrantyMonths;

	public Electronics(int id, String name, double price, int stock, int warrantyMonths) {
		super(id, name, price, stock);
		this.warrantyMonths = warrantyMonths;
	}

	public int getWarrantyMonths() {
		return warrantyMonths;
	}

	public void setWarrantyMonths(int warrantyMonths) {
		this.warrantyMonths = warrantyMonths;
	}

	@Override
	public String getDescription() {
		return getName() + " (" + warrantyMonths + " months warranty)";
	}
}
