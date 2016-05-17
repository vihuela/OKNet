package ricky.oknet.callback;

import android.support.annotation.Nullable;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import ricky.oknet.exception.ExceptionParseMgr;
import ricky.oknet.exception.ExceptionParser;
import ricky.oknet.exception.InternalExceptionParser;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.utils.Cons;

/**
 * 抽象的回调接口
 */
public abstract class AbsCallback<T> {

    /**
     * 请求网络开始前，UI线程
     */
    public void onBefore(BaseRequest request) {
    }

    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     */
    public abstract T parseNetworkResponse(Response response);

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public abstract void onResponse(boolean isFromCache, T t, Request request, @Nullable Response response);

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
        if (e != null) {
            parseOKNetException(e);
        }
    }

    /**
     * 经过异常解析之后的精确错误回调，如果需要原始信息，请重写 onError 的多参回调
     */
    public abstract void onSimpleError(Cons.Error error, String message);

    /**
     * 用户端可调用此方法协助处理异常，最终会回调至重写的onSimpleError()
     */
    final protected void parseOKNetException(final Exception e) {
        ExceptionParseMgr.Instance.parseException(e, new ExceptionParser.IHandler() {
            @Override
            public void onHandler(Cons.Error error, String message) {
                onSimpleError(error, message);
            }
        });
    }

    /**
     * 添加用户自定义的异常解析，可参考：{@link InternalExceptionParser}
     */
    final protected void addExceptionParser(ExceptionParser parser) {
        ExceptionParseMgr.Instance.addParse(parser);
    }

    /**
     * 请求网络结束后，UI线程
     */
    public void onAfter(boolean isFromCache, @Nullable T t, Call call, @Nullable Response response, @Nullable Exception e) {
    }

    /**
     * Post执行上传过程中的进度回调，get请求不回调，UI线程
     *
     * @param currentSize  当前上传的字节数
     * @param totalSize    总共需要上传的字节数
     * @param progress     当前上传的进度
     * @param networkSpeed 当前上传的速度   字节/秒
     */
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }

    /**
     * 执行下载过程中的进度回调，UI线程
     *
     * @param currentSize  当前下载的字节数
     * @param totalSize    总共需要下载的字节数
     * @param progress     当前下载的进度
     * @param networkSpeed 当前下载的速度   字节/秒
     */
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }

    public static final AbsCallback CALLBACK_DEFAULT = new AbsCallback() {

        @Override
        public Response parseNetworkResponse(Response response) {
            return response;
        }

        @Override
        public void onResponse(boolean isFromCache, Object data, Request request, Response response) {
        }

        @Override
        public void onSimpleError(Cons.Error error, String message) {

        }
    };
}