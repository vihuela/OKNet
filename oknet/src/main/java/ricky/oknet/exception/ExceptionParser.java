package ricky.oknet.exception;


import ricky.oknet.utils.Cons;

/**
 * Error dispatch
 */
public abstract class ExceptionParser {
    protected ExceptionParser nextParser;

    public void handleException(Throwable e, IHandler handler) {
        //e should not be null ...
        if (!handler(e, handler)) {
            if (!handler(e != null ? e.getCause() : null, handler)) {
                getNextParser().handleException(e, handler);
            }
        }
    }

    /**
     * @return true is resume error
     */
    protected abstract boolean handler(Throwable e, IHandler handler);

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
