package ricky.oknet.convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

public class BitmapConvert implements Converter<Bitmap> {

    public static BitmapConvert create() {
        return BitmapConvert.ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static BitmapConvert convert = new BitmapConvert();
    }

    @Override
    public Bitmap convertSuccess(Response value) throws Exception {
        return BitmapFactory.decodeStream(value.body().byteStream());
    }
}