package ricky.oknet.callback;

import android.graphics.Bitmap;


import okhttp3.Response;
import ricky.oknet.convert.BitmapConvert;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/12
 * 描    述：返回图片的Bitmap，这里没有进行图片的缩放，可能会发生 OOM
 * 修订历史：
 * ================================================
 */
public abstract class BitmapCallback extends AbsCallback<Bitmap> {

    @Override
    public Bitmap convertSuccess(Response response) throws Exception {
        Bitmap bitmap = BitmapConvert.create().convertSuccess(response);
        response.close();
        return bitmap;
    }
}