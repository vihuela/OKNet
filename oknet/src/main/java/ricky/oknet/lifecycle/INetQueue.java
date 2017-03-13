package ricky.oknet.lifecycle;

import ricky.oknet.retrofit.Net;

/**
 * @author ricky.yao on 2016/6/15.
 */
public interface INetQueue {

    void add(Net<?> net);

    void cancel();

}
