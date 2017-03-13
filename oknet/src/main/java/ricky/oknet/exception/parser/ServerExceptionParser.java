package ricky.oknet.exception.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import ricky.oknet.exception.ServerException;
import ricky.oknet.utils.Cons;
import ricky.oknet.utils.Error;


class ServerExceptionParser extends ExceptionParser {

    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        if (ServerException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Error.Server, getMessageFromThrowable(e));
            return true;
        }
        return false;
    }
}
