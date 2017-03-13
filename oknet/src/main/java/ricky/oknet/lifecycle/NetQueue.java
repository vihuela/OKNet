package ricky.oknet.lifecycle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ricky.oknet.retrofit.Net;
import rx.Subscription;

/**
 * @author ricky.yao on 2016/6/15.
 *         配合网络管理生命周期
 */
public class NetQueue implements INetQueue {

    private List<Net<?>> mQueue = new LinkedList<>();
    private List<Subscription> mSubscriptionQueue = new LinkedList<>();

    @Override
    public void add(Net<?> net) {
        mQueue.add(net);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        mSubscriptionQueue.add(subscription);
    }

    @Override
    public void cancel() {
        //normal mode
        ArrayList<Net<?>> queueCopy = new ArrayList<>();
        queueCopy.addAll(this.mQueue);

        this.mQueue.clear();
        for (Net<?> mNet : queueCopy) {
            mNet.cancel();
        }
        //rx mode
        ArrayList<Subscription> queueSubCopy = new ArrayList<>();
        queueSubCopy.addAll(this.mSubscriptionQueue);


        this.mSubscriptionQueue.clear();
        for (Subscription s : queueSubCopy) {
            if (!s.isUnsubscribed()) {
                s.unsubscribe();
            }
        }
    }
}
