import java.util.List;

public interface ProductDAO {
    void saveProduct(Product product);
    Product findProduct(int id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);
}
