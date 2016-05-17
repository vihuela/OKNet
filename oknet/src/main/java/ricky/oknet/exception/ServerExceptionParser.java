package ricky.oknet.exception;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import ricky.oknet.utils.Cons;


public class ServerExceptionParser extends ExceptionParser {

    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

        if (HttpException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Cons.Error.Server, s);
            return true;
        }
        return false;
    }
}
