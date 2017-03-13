package ricky.oknet.exception.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import ricky.oknet.utils.Cons;
import ricky.oknet.utils.Error;

class UnknowExceptionParser extends ExceptionParser {

    /**
     * must return true
     */
    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        handler.onHandler(Error.UnKnow, getMessageFromThrowable(e));
        return true;
    }
}
