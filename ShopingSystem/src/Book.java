public class Book extends Product {
	private String author;

	public Book(int id, String name, double price, int stock, String author) {
		super(id, name, price, stock);
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getDescription() {
		return getName() + " by " + author;
	}
}
