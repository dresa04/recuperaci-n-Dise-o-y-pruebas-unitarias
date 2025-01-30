package data.Exceptions;

public class InvalidGeographicPointException extends RuntimeException {
  public InvalidGeographicPointException(String message) {
    super(message);
  }
}