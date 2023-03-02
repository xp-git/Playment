package cn.j3code.config.exception;


import cn.j3code.config.vo.FailInfo;

public class ApCodeException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public ApCodeException() {
    }

    public ApCodeException(Integer code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
    }

    public ApCodeException(String message, Object... args) {
        super(String.format(message, args));
        this.code = FailInfo.DEFAULT_CODE;
    }

    public ApCodeException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
        this.code = FailInfo.DEFAULT_CODE;
    }

    public ApCodeException(Throwable cause) {
        super(cause);
        this.code = FailInfo.DEFAULT_CODE;
    }

}
