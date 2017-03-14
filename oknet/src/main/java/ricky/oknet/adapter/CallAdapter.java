package ricky.oknet.adapter;

public interface CallAdapter<T> {

    /** call执行的代理方法 */
    <R> T adapt(Call<R> call);
}