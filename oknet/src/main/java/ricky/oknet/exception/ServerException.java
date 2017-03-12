package ricky.oknet.exception;

/**
 * @author Ricky.yao on 2016/5/16.
 */
public class ServerException extends Exception {

    public static ServerException INSTANCE(String msg) {
        return new ServerException(msg);
    }
    public ServerException(String detailMessage) {
        super(detailMessage);
    }
}
