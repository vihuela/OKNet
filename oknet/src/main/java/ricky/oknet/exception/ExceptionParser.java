package ricky.oknet.exception;


import android.support.annotation.NonNull;

import ricky.oknet.utils.Cons;

/**
 * Error dispatch
 */
public abstract class ExceptionParser {
    protected ExceptionParser nextParser;

    public void handleException(Throwable e, IHandler handler) {
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
    protected abstract boolean handler(@NonNull Throwable e, @NonNull IHandler handler);

    public ExceptionParser getNextParser() {
        return nextParser;
    }

    public void setNextParser(ExceptionParser nextParser) {
        this.nextParser = nextParser;
    }

    public interface IHandler {
        void onHandler(Cons.Error error, String message);
    }

}
