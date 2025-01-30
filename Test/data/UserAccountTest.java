package data;

import data.Exceptions.InvalidUserAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount("123", "testUser", "test@example.com", "password123", 100);
    }

    @Test
    void testConstructor_ValidParameters() {
        assertNotNull(userAccount);
        assertEquals("123", userAccount.getUserId());
        assertEquals("testUser", userAccount.getUsername());
        assertEquals("test@example.com", userAccount.getEmail());
        assertEquals(100, userAccount.getMonedero());
    }

    @Test
    void testConstructor_InvalidUserId() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("", "testUser", "test@example.com", "password123", 100));
        assertEquals("El identificador de usuario no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void testConstructor_InvalidUsername() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("123", "", "test@example.com", "password123", 100));
        assertEquals("El nombre de usuario no puede ser nulo o vacío.", exception.getMessage());
    }

    @Test
    void testConstructor_InvalidEmail() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("123", "testUser", "invalid-email", "password123", 100));
        assertEquals("Dirección de correo electrónico no válida.", exception.getMessage());
    }

    @Test
    void testConstructor_InvalidPassword() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("123", "testUser", "test@example.com", "123", 100));
        assertEquals("La contraseña debe tener al menos 6 caracteres.", exception.getMessage());
    }

    @Test
    void testConstructor_NegativeMonedero() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("123", "testUser", "test@example.com", "password123", -10));
        assertEquals("El saldo del monedero no puede ser negativo.", exception.getMessage());
    }

    @Test
    void testGetters() {
        assertEquals("123", userAccount.getUserId());
        assertEquals("testUser", userAccount.getUsername());
        assertEquals("test@example.com", userAccount.getEmail());
        assertEquals(100, userAccount.getMonedero());
    }

    @Test
    void testSetMonedero() {
        userAccount.setMonedero(200);
        assertEquals(200, userAccount.getMonedero());

        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                userAccount.setMonedero(-50));
        assertEquals("El saldo del monedero no puede ser negativo.", exception.getMessage());
    }

    @Test
    void testVerifyPassword_CorrectPassword() {
        assertTrue(userAccount.verifyPassword("password123"));
    }

    @Test
    void testVerifyPassword_IncorrectPassword() {
        assertFalse(userAccount.verifyPassword("wrongPassword"));
    }

    @Test
    void testEquals_SameValues() {
        UserAccount otherAccount = new UserAccount("123", "testUser", "test@example.com", "password456", 200);
        assertEquals(userAccount, otherAccount);
    }

    @Test
    void testEquals_DifferentValues() {
        UserAccount otherAccount = new UserAccount("456", "otherUser", "other@example.com", "password456", 200);
        assertNotEquals(userAccount, otherAccount);
    }

    @Test
    void testToString() {
        assertEquals("UserAccount{userId='123', username='testUser', email='test@example.com'}", userAccount.toString());
    }
}
