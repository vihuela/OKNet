package ricky.oknets.exception;

/**
 * @author Ricky.yao on 2016/5/16.
 */
public class TokenException extends Exception {
    public TokenException() {
        super("token失效");
    }

    public TokenException(String m) {
        super(m);
    }
}
