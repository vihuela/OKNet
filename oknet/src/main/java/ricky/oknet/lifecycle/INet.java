package ricky.oknet.lifecycle;

import ricky.oknet.callback.AbsCallback;
import ricky.oknet.convert.Converter;
import rx.Observable;

public interface INet<T> {

    void execute(AbsCallback<T> callback, INetQueue... iNetQueue);

    Observable<T> rx(Converter<T> converter, AbsCallback<T> callback, INetQueue... iNetQueue);

    void cancel();
}
