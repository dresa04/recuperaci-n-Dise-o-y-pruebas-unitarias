package data;

import data.Exceptions.InvalidUserAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    private UserAccount usuario1;
    private UserAccount usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new UserAccount("U123", "CarlosPerez", "carlosperez@example.com", "claveSegura123", 50);
        usuario2 = new UserAccount("U456", "AnaLopez", "analopez@example.com", "miContraseña", 100);
    }

    @Test
    void testConstructor_UsuarioValido() {
        assertNotNull(usuario1);
        assertEquals("U123", usuario1.getUserId());
        assertEquals("CarlosPerez", usuario1.getUsername());
        assertEquals("carlosperez@example.com", usuario1.getEmail());
        assertEquals("claveSegura123", usuario1.getPassword());
        assertEquals(50, usuario1.getMonedero());
    }

    @Test
    void testConstructor_IdentificadorNulo() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount(null, "Usuario", "usuario@example.com", "contraseña123", 0)
        );
        assertEquals("L'identificador d'usuari no pot ser null o buit.", exception.getMessage());
    }

    @Test
    void testConstructor_IdentificadorVacio() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("", "Usuario", "usuario@example.com", "contraseña123", 0)
        );
        assertEquals("L'identificador d'usuari no pot ser null o buit.", exception.getMessage());
    }

    @Test
    void testConstructor_NombreUsuarioNulo() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("U789", null, "usuario@example.com", "contraseña123", 0)
        );
        assertEquals("El nom d'usuari no pot ser null o buit.", exception.getMessage());
    }

    @Test
    void testConstructor_CorreoInvalido() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("U789", "Usuario", "correo-invalido", "contraseña123", 0)
        );
        assertEquals("Adreça de correu electrònic no vàlida.", exception.getMessage());
    }

    @Test
    void testConstructor_ContraseñaCorta() {
        Exception exception = assertThrows(InvalidUserAccountException.class, () ->
                new UserAccount("U789", "Usuario", "usuario@example.com", "123", 0)
        );
        assertEquals("La contrasenya ha de tenir almenys 6 caràcters.", exception.getMessage());
    }

    @Test
    void testSetMonedero() {
        usuario1.setMonedero(200);
        assertEquals(200, usuario1.getMonedero());
    }

    @Test
    void testVerificarContraseña_Correcta() {
        assertTrue(usuario1.verifyPassword("claveSegura123"));
    }

    @Test
    void testVerificarContraseña_Incorrecta() {
        assertFalse(usuario1.verifyPassword("claveIncorrecta"));
    }

    @Test
    void testEquals_MismoUsuario() {
        UserAccount usuario3 = new UserAccount("U123", "CarlosPerez", "carlosperez@example.com", "nuevaClave", 30);
        assertEquals(usuario1, usuario3, "Dos cuentas con el mismo ID, nombre de usuario y correo deberían ser iguales.");
    }

    @Test
    void testEquals_DiferenteUsuario() {
        assertNotEquals(usuario1, usuario2);
    }

    @Test
    void testHashCode() {
        UserAccount usuario3 = new UserAccount("U123", "CarlosPerez", "carlosperez@example.com", "nuevaClave", 30);
        assertEquals(usuario1.hashCode(), usuario3.hashCode(), "El hashCode debería ser igual para objetos con el mismo ID, nombre de usuario y correo.");
    }

    @Test
    void testToString() {
        String esperado = "data.data.UserAccount{userId='U123', username='CarlosPerez', email='carlosperez@example.com'}";
        assertEquals(esperado, usuario1.toString());
    }
}
