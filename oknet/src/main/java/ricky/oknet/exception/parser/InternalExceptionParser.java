package ricky.oknet.exception.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import ricky.oknet.utils.Cons;
import ricky.oknet.utils.Error;

/**
 * only json error check
 */
public class InternalExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {

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
