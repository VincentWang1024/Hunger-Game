package exception;

public class InvalidLicenseNoException extends SimpleBankingGlobalException {
    public InvalidLicenseNoException(String message, String code) {
        super(message, code);
    }
}