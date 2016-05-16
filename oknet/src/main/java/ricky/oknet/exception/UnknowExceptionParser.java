package ricky.oknet.exception;

import android.text.TextUtils;


import ricky.oknet.utils.Cons;

public class UnknowExceptionParser extends ExceptionParser {

    /**
     * must return true
     */
    @Override protected boolean handler(Throwable e, IHandler handler) {
        String s = e != null ? !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName() : "unKnow";
        handler.onHandler(Cons.Error.UnKnow, s);
        return true;
    }
}
