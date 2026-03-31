
import org.example.services.AuthService;
import org.example.repositories.UserRepository;
import org.example.models.Role;
import org.example.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

    private AuthService authService;
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        mockUserRepository = new UserRepository() {
            @Override
            public User getUser(String login) {
                if ("admin".equals(login)) {
                    String hashForAdmin123 = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9";
                    return new User("admin", hashForAdmin123, Role.ADMIN, null);
                }
                return null;
            }

            @Override public List<User> getUsers() { return null; }
            public void save() {}
            public void load() {}
            @Override public void update(User user) {}

            @Override
            public void addUser(User user) {

            }

            @Override
            public boolean removeUser(String login) {
                return false;
            }
        };

        authService = new AuthService(mockUserRepository);
    }

    @Test
    void loginWithCorrectCredentialsShouldReturnUser() {
        String login = "admin";
        String password = "admin123";

        User result = authService.login(login, password);

        assertNotNull(result, "Użytkownik nie powinien być nullem przy poprawnych danych");
        assertEquals("admin", result.getLogin(), "Login powinien się zgadzać");
        assertEquals(Role.ADMIN, result.getRole(), "Rola powinna być ADMIN");
    }

    @Test
    void loginWithIncorrectPasswordShouldReturnNull() {
        String login = "admin";
        String wrongPassword = "wrongPassword";

        User result = authService.login(login, wrongPassword);

        assertNull(result, "Metoda login powinna zwrócić null przy błędnym haśle");
    }

    @Test
    void loginWithNonExistentUserShouldReturnNull() {
        String nonExistentLogin = "nonExistentUser";
        String password = "admin123";

        User result = authService.login(nonExistentLogin, password);

        assertNull(result, "Metoda login powinna zwrócić null, gdy użytkownik nie istnieje w bazie");
    }
}