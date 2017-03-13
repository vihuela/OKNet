package ricky.oknet.exception.parser;

import ricky.oknet.exception.ServerException;
import ricky.oknet.utils.Error;


class ServerExceptionParser extends ExceptionParser {

    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (ServerException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Error.Server, getMessageFromThrowable(e));
            return true;
        }
        return false;
    }
}
