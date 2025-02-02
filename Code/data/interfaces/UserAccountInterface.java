package data.interfaces;

public interface UserAccountInterface {

    // Getters
    String getUserId();

    String getUsername();

    String getEmail();

    String getPassword();

    int getMonedero();

    void setMonedero(int nou_saldo);

    // Mètode per verificar la contrasenya
    boolean verifyPassword(String inputPassword);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();
}
