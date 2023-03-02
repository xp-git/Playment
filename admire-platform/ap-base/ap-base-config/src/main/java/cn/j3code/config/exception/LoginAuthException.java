package cn.j3code.config.exception;


public class LoginAuthException extends RuntimeException {
    public LoginAuthException() {
    }
    public LoginAuthException(String message, Object... args) {
        super(String.format(message, args));
    }

    public LoginAuthException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public LoginAuthException(Throwable cause) {
        super(cause);
    }

}
