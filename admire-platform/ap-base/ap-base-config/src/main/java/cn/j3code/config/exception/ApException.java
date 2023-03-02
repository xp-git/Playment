package cn.j3code.config.exception;


import cn.j3code.config.enums.ApExceptionEnum;

public class ApException extends RuntimeException {
    public ApException() {
    }

    public ApException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ApException(ApExceptionEnum apExceptionEnum, Object... args) {
        super(String.format(apExceptionEnum.getDescription(), args));
    }

    public ApException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public ApException(Throwable cause) {
        super(cause);
    }

}
