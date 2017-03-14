package ricky.oknet.exception.parser;


import android.text.TextUtils;

import ricky.oknet.utils.Error;

public abstract class ExceptionParser {
    private ExceptionParser nextParser;

    public static String getMessageFromThrowable(Throwable e) {
        return !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
    }

    void handleException(Throwable e, IHandler handler) {
        //first
        if (e != null && !handler(e, handler)) {
            //second
            if (e.getCause() != null && !handler(e.getCause(), handler)) {
                //third
                next(e, handler);
            } else {
                next(e, handler);
            }
        }
    }

    private void next(Throwable e, IHandler handler) {
        if (getNextParser() != null) getNextParser().handleException(e, handler);
    }

    /**
     * @return true is resume error
     */
    protected abstract boolean handler(Throwable e, IHandler handler);

    private ExceptionParser getNextParser() {
        return nextParser;
    }

    void setNextParser(ExceptionParser nextParser) {
        this.nextParser = nextParser;
    }

    public interface IHandler {
        void onHandler(Error error, String message);
    }

}
