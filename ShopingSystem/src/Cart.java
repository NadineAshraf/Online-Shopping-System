import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Cart {
	private Map<Product, Integer> items = new HashMap<>();
	private Stack<Map<Product, Integer>> undoStack = new Stack<>();

	private void saveState() {
		Map<Product, Integer> copy = new HashMap<>(items);
		undoStack.push(copy);
	}

	public void addProduct(Product p, int qty) {
		if (p == null || qty <= 0) return;
		saveState();
		items.put(p, items.getOrDefault(p, 0) + qty);
	}

	public void removeProduct(Product p) {
		if (p == null) return;
		if (items.containsKey(p)) {
			saveState();
			items.remove(p);
		}
	}

	public void updateQuantity(Product p, int qty) {
		if (p == null) return;
		if (qty <= 0) {
			removeProduct(p);
			return;
		}
		if (items.containsKey(p)) {
			saveState();
			items.put(p, qty);
		}
	}

	public void undo() {
		if (!undoStack.isEmpty()) {
			items = undoStack.pop();
		}
	}

	public Map<Product, Integer> getItems() {
		return items;
	}

	public double getTotal() {
		double total = 0.0;
		for (Map.Entry<Product, Integer> e : items.entrySet()) {
			total += e.getKey().getPrice() * e.getValue();
		}
		return total;
	}

	public void clear() {
		saveState();
		items.clear();
	}
}
