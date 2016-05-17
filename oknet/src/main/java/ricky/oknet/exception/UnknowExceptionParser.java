package ricky.oknet.exception;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import ricky.oknet.utils.Cons;

public class UnknowExceptionParser extends ExceptionParser {

    /**
     * must return true
     */
    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
        handler.onHandler(Cons.Error.UnKnow, s);
        return true;
    }
}
