package ricky.oknet.callback;


import okhttp3.Response;
import ricky.oknet.convert.StringConvert;

public abstract class StringCallback extends AbsCallback<String> {

    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        return s;
    }
}