package ricky.oknet.exception;

public class OkGoException extends Exception {

    public static OkGoException INSTANCE(String msg) {
        return new OkGoException(msg);
    }

    public OkGoException() {
        super();
    }

    public OkGoException(String detailMessage) {
        super(detailMessage);
    }

    public OkGoException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OkGoException(Throwable throwable) {
        super(throwable);
    }
}