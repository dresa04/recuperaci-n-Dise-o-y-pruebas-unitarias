package data.interfaces;

import data.Exceptions.InvalidUserAccountException;

import java.util.Objects;

/**
 * Clase que representa una cuenta de usuario en el sistema de micromovilidad.
 * Cada usuario tiene un identificador único, nombre de usuario, correo electrónico,
 * contraseña y un monedero con saldo disponible.
 */
public class UserAccount implements UserAccountInterface {
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private int monedero;

    /**
     * Crea una nueva cuenta de usuario con la información proporcionada.
     *
     * @param userId    Identificador único del usuario. No puede ser nulo ni vacío.
     * @param username  Nombre del usuario. No puede ser nulo ni vacío.
     * @param email     Correo electrónico del usuario. Debe tener un formato válido.
     * @param password  Contraseña del usuario. Debe tener al menos 6 caracteres.
     * @param monedero  Saldo inicial del usuario en el monedero.
     * @throws InvalidUserAccountException Si alguno de los parámetros no cumple los requisitos.
     */
    public UserAccount(String userId, String username, String email, String password, int monedero) {
        if (userId == null || userId.isEmpty()) {
            throw new InvalidUserAccountException("L'identificador d'usuari no pot ser null o buit.");
        }
        if (username == null || username.isEmpty()) {
            throw new InvalidUserAccountException("El nom d'usuari no pot ser null o buit.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidUserAccountException("Adreça de correu electrònic no vàlida.");
        }
        if (password == null || password.length() < 6) {
            throw new InvalidUserAccountException("La contrasenya ha de tenir almenys 6 caràcters.");
        }
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.monedero = monedero;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return ID del usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el saldo actual del monedero del usuario.
     *
     * @return Saldo en el monedero.
     */
    public int getMonedero() {
        return monedero;
    }

    /**
     * Establece un nuevo saldo en el monedero del usuario.
     *
     * @param nou_saldo Nuevo saldo del monedero.
     */
    public void setMonedero(int nou_saldo) {
        this.monedero = nou_saldo;
    }

    /**
     * Verifica si la contraseña ingresada coincide con la contraseña del usuario.
     *
     * @param inputPassword Contraseña ingresada para verificación.
     * @return {@code true} si la contraseña coincide, {@code false} en caso contrario.
     */
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    /**
     * Compara esta cuenta de usuario con otra para determinar si son iguales.
     * Dos cuentas son iguales si tienen el mismo userId, username y email.
     *
     * @param o Objeto con el que se compara.
     * @return {@code true} si ambos objetos representan la misma cuenta de usuario, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId.equals(that.userId) &&
                username.equals(that.username) &&
                email.equals(that.email);
    }

    /**
     * Genera un código hash basado en el userId, username y email del usuario.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena con el formato "UserAccount {userId=X, username=Y, email=Z}".
     */
    @Override
    public String toString() {
        return "UserAccount{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
