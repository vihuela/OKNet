package ricky.oknet.exception.parser;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import ricky.oknet.utils.Error;

/**
 * only json error check
 */
class InternalExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(Throwable e, IHandler handler) {

        if (NumberFormatException.class.isAssignableFrom(e.getClass()) ||
                JsonParseException.class.isAssignableFrom(e.getClass()) ||
                com.google.gson.JsonSyntaxException.class.isAssignableFrom(e.getClass()) ||
                JSONException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Error.Internal, getMessageFromThrowable(e));
            return true;
        }
        return false;
    }
}
