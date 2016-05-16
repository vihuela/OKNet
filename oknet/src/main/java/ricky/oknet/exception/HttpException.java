package ricky.oknet.exception;

/**
 * @author YaoWeihui on 2016/5/16.
 * OKHttp并没有HttpException相关的类，这里手动创建
 */
public class HttpException extends Exception {

    public HttpException(String detailMessage) {
        super(detailMessage);
    }
}
