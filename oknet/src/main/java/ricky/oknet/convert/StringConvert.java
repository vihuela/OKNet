package ricky.oknet.convert;

import okhttp3.Response;

public class StringConvert implements Converter<String> {

    public static StringConvert create() {
        return ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static StringConvert convert = new StringConvert();
    }

    @Override
    public String convertSuccess(Response value) throws Exception {
        return value.body().string();
    }
}
