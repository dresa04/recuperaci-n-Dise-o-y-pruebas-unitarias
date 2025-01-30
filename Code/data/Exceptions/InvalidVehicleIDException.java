package data.Exceptions;

public class InvalidVehicleIDException extends RuntimeException {
  public InvalidVehicleIDException(String message) {
    super(message);
  }
}