package data.interfaces;


public interface UserAccountInterface {
    String getUserId();
    String getUsername();
    String getEmail();
    int getMonedero();
    void setMonedero(int nuevoSaldo);
    boolean verifyPassword(String inputPassword);
}
