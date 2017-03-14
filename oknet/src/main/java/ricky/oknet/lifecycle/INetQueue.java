package ricky.oknet.lifecycle;

import ricky.oknet.retrofit.Net;
import rx.Subscription;

public interface INetQueue {

    //normal
    void add(Net<?> net);

    //rx
    void addSubscription(Subscription subscription);

    void cancel();

}
