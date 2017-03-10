package ricky.oknet.rx;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * A version of {@link Observable#map(Func1)} which lets us trigger {@code onError} without having
 * to use {@link Observable#flatMap(Func1)} which breaks producer requests from propagating.
 */
final class OperatorMapResponseToBodyOrError<T> implements Operator<T, ricky.oknet.model.Response<T>> {

    private static final OperatorMapResponseToBodyOrError<Object> INSTANCE = new OperatorMapResponseToBodyOrError<>();

    @SuppressWarnings("unchecked") // Safe because of erasure.
    static <R> OperatorMapResponseToBodyOrError<R> instance() {
        return (OperatorMapResponseToBodyOrError<R>) INSTANCE;
    }

    @Override
    public Subscriber<? super ricky.oknet.model.Response<T>> call(final Subscriber<? super T> child) {
        return new Subscriber<ricky.oknet.model.Response<T>>(child) {
            @Override
            public void onNext(ricky.oknet.model.Response<T> response) {
                if (response.isSuccessful()) {
                    child.onNext(response.body());
                } else {
                    child.onError(new HttpException(response));
                }
            }

            @Override
            public void onCompleted() {
                child.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                child.onError(e);
            }
        };
    }
}
