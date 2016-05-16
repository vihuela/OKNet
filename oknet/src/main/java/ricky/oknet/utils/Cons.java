package ricky.oknet.utils;

/**
 * @author YaoWeihui on 2016/5/16.
 */
public interface Cons {
    String IO_EXCEPTION = "Canceled";
    String SOCKET_EXCEPTION = "Socket closed";

    enum Error {
        NetWork,//net not response or timeout etc
        Internal,//json parse error etc
        Server,//404.500 ..etc
        UnKnow,
        Invalid//request success but no need
    }
}
