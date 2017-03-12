package ricky.oknet.rx;

import ricky.oknet.model.Response;

/** Exception for an unexpected, non-2xx HTTP response. */
public final class HttpException extends Exception {
    private final int code;
    private final String message;
    private final transient ricky.oknet.model.Response<?> response;

    public HttpException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message());
        this.code = response.code();
        this.message = response.message();
        this.response = response;
    }

    /** HTTP status code. */
    public int code() {
        return code;
    }

    /** HTTP status message. */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public ricky.oknet.model.Response<?> response() {
        return response;
    }
}
