package exception;

public class InvalidUserNameException extends SimpleBankingGlobalException {
    public InvalidUserNameException(String message, String code) {
        super(message, code);
    }
}