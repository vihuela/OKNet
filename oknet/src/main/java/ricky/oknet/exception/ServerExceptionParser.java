package ricky.oknet.exception;

import android.text.TextUtils;

import ricky.oknet.utils.Cons;


public class ServerExceptionParser extends ExceptionParser {

    @Override protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

            if (HttpException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Cons.Error.Server, s);
                return true;
            }
        }
        return false;
    }
}
