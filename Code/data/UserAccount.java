package data;

import data.interfaces.UserAccountInterface;

import java.util.Objects;

public class UserAccount implements UserAccountInterface {
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private int monedero;

    public UserAccount(String userId, String username, String email, String password, int monedero) {
        if (userId == null || userId.isEmpty()) {
            throw new InvalidUserAccountException("El identificador de usuario no puede ser nulo o vacío.");
        }
        if (username == null || username.isEmpty()) {
            throw new InvalidUserAccountException("El nombre de usuario no puede ser nulo o vacío.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidUserAccountException("Dirección de correo electrónico no válida.");
        }
        if (password == null || password.length() < 6) {
            throw new InvalidUserAccountException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (monedero < 0) {
            throw new InvalidUserAccountException("El saldo del monedero no puede ser negativo.");
        }

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.monedero = monedero;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getMonedero() {
        return monedero;
    }

    @Override
    public void setMonedero(int nuevoSaldo) {
        if (nuevoSaldo < 0) {
            throw new InvalidUserAccountException("El saldo del monedero no puede ser negativo.");
        }
        this.monedero = nuevoSaldo;
    }

    @Override
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
        return "UserAccount{userId='" + userId + "', username='" + username + "', email='" + email + "'}";
    }


    public static class InvalidUserAccountException extends RuntimeException {
        public InvalidUserAccountException(String message) {
            super(message);
        }
    }

}
