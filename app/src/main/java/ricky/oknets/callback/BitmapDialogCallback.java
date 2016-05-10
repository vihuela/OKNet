package ricky.oknets.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.Window;


import okhttp3.Call;
import okhttp3.Response;
import ricky.oknet.callback.BitmapCallback;
import ricky.oknet.request.BaseRequest;

/**
 * 请求图图片的时候显示对话框
 */
public abstract class BitmapDialogCallback extends BitmapCallback {

    private ProgressDialog dialog;

    public BitmapDialogCallback(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    @Override
    public void onBefore(BaseRequest request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onAfter(boolean isFromCache, @Nullable Bitmap bitmap, Call call, @Nullable Response response, @Nullable Exception e) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
