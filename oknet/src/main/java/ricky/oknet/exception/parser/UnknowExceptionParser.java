package ricky.oknet.exception.parser;

import ricky.oknet.utils.Error;

class UnknowExceptionParser extends ExceptionParser {

    /**
     * must return true
     */
    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        handler.onHandler(Error.UnKnow, getMessageFromThrowable(e));
        return true;
    }
}
