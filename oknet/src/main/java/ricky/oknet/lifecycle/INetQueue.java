package ricky.oknet.lifecycle;

import ricky.oknet.retrofit.Net;
import rx.Subscription;

/**
 * @author ricky.yao on 2016/6/15.
 */
public interface INetQueue {

    //normal
    void add(Net<?> net);

    //rx
    void addSubscription(Subscription subscription);

    void cancel();

}
