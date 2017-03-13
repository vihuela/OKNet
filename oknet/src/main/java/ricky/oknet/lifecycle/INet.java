package ricky.oknet.lifecycle;

import ricky.oknet.callback.AbsCallback;

/**
 * @author ricky.yao on 2016/6/15.
 */
public interface INet<T> {

    void execute(AbsCallback<T> callback, INetQueue... iNetQueue);

    void cancel();
}
