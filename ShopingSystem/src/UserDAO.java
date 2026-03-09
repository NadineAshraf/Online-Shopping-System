import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    User findUser(String username);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(String username);
}
