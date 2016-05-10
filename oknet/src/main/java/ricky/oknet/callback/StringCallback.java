package ricky.oknet.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 返回字符串类型的数据
 */
public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String parseNetworkResponse(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
