package ricky.oknet.lifecycle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ricky.oknet.retrofit.Net;

/**
 * @author ricky.yao on 2016/6/15.
 *         配合网络管理生命周期
 */
public class NetQueue implements INetQueue {

    private List<Net<?>> mQueue = new LinkedList<>();

    @Override
    public void add(Net<?> net) {
        mQueue.add(net);
    }

    @Override
    public void cancel() {
        ArrayList<Net<?>> queueCopy = new ArrayList<>();
        queueCopy.addAll(this.mQueue);

        this.mQueue.clear();
        for (Net<?> mNet : queueCopy) {
            mNet.cancel();
        }
    }
}
