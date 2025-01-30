package data.Exceptions;

public class InvalidStationIDException extends RuntimeException {
  public InvalidStationIDException(String message) {
    super(message);
  }
}
