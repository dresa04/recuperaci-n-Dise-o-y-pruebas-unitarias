package data;

import data.Exceptions.InvalidUserAccountException;
import data.interfaces.UserAccountInterface;

import java.util.Objects;

/**
 * Classe que representa un compte d'usuari.
 */
public class UserAccount implements UserAccountInterface {
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private int monedero;

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

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getMonedero() {
        return monedero;
    }

    public void setMonedero(int nou_saldo) {
        this.monedero = nou_saldo;
    }

    // Mètode per verificar la contrasenya
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId.equals(that.userId) &&
                username.equals(that.username) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }

    @Override
    public String toString() {
        return "data.data.UserAccount{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
