package ricky.oknets.callback;

import android.text.TextUtils;

import com.google.gson.stream.JsonReader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import ricky.oknet.convert.Converter;
import ricky.oknet.utils.DataConvert;
import ricky.oknets.common.CommonResponse;
import ricky.oknets.exception.TokenException;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：
 * -
 * -
 * -
 * -该类主要用于 OkRx 调用，直接解析泛型，返回数据对象，若不用okrx，可以删掉该类
 * -
 * -
 * -
 * 修订历史：
 * ================================================
 */
public class JsonConvert<T> implements Converter<T> {

    
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
}