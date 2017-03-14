package ricky.oknet.callback;

import android.graphics.Bitmap;


import okhttp3.Response;
import ricky.oknet.convert.BitmapConvert;

public abstract class BitmapCallback extends AbsCallback<Bitmap> {

    @Override
    public Bitmap convertSuccess(Response response) throws Exception {
        Bitmap bitmap = BitmapConvert.create().convertSuccess(response);
        response.close();
        return bitmap;
    }
}