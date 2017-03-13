package ricky.oknets.callback;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.stream.JsonReader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;
import ricky.oknet.callback.AbsCallback;
import ricky.oknet.exception.parser.ExceptionParser;
import ricky.oknet.request.BaseRequest;
import ricky.oknet.utils.DataConvert;
import ricky.oknet.utils.Error;
import ricky.oknets.common.CommonResponse;
import ricky.oknets.exception.TokenException;

/**
 * ricky.yao
 * @param <T>
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {


    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);

        //动态加入头字段、参数
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");

        //自定义异常解析
        addExceptionParser(new ExceptionParser() {
            @Override
            protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {

                if (TokenException.class.isAssignableFrom(e.getClass())) {
                    handler.onHandler(Error.Invalid, getMessageFromThrowable(e));
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public T convertSuccess(Response response) throws Exception {
        Type type = null;
        try {
            type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            //没有传递泛型
            e.printStackTrace();
        }
        if (type == String.class) {
            String responseData = response.body().string();
            T t = TextUtils.isEmpty(responseData) ? (T) response.toString() : (T) responseData;
            response.close();
            return t;
        } else {
            JsonReader jsonReader = new JsonReader(response.body().charStream());
            T t = DataConvert.fromJson(jsonReader, type);
            response.close();
            //与业务有关
            if (CommonResponse.class.isInstance(t)) {
                CommonResponse t1 = (CommonResponse) t;
                //t1.code = 10086;//测试自定义异常
                switch (t1.code) {
                    //返回正确
                    case 0:
                        return t;
                    case 10086:
                        throw new TokenException("token is bad");
                    default:
                        throw new IllegalArgumentException("unKnow error");
                }

            } else {
                return t;
            }

        }
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
        success(t, false);
    }

    @Override
    public void onCacheSuccess(T t, Call call) {
        super.onCacheSuccess(t, call);
        success(t, true);

    }

    @Override
    public void onParsedError(Error error, String message) {
        super.onParsedError(error, message);
        error(error, message);
    }

    /**
     * 通常实现以下回调即可--------------------------------------------------------------------------
     */

    /**
     * success
     *
     * @param t         t
     * @param fromCache 是否来自缓存
     */
    public abstract void success(T t, boolean fromCache);

    /**
     * error
     *
     * @param error   错误类型
     * @param message 错误详细文本
     */
    public void error(Error error, String message) {
    }


}