package ricky.oknet.lifecycle;

import ricky.oknet.callback.AbsCallback;
import ricky.oknet.convert.Converter;
import rx.Observable;

/**
 * @author ricky.yao on 2016/6/15.
 */
public interface INet<T> {

    void execute(AbsCallback<T> callback, INetQueue... iNetQueue);

    Observable<T> rx(Converter<T> converter, AbsCallback<T> callback, INetQueue... iNetQueue);

    void cancel();
}
