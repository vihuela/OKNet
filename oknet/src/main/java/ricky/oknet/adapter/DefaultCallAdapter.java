package ricky.oknet.adapter;

public class DefaultCallAdapter<T> implements CallAdapter<Call<T>> {

    public static <T> DefaultCallAdapter<T> create() {
        return new DefaultCallAdapter<>();
    }

    @Override
    public <R> Call<T> adapt(Call<R> call) {
        return (Call<T>) call;
    }
}