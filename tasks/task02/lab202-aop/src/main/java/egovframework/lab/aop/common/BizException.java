package egovframework.lab.aop.common;

public class BizException extends Exception {

    private static final long serialVersionUID = 1L;

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
