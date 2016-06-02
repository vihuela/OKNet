package ricky.oknet.exception;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import ricky.oknet.utils.Cons;

/**
 * only json error check
 */
public class InternalExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

        if (NumberFormatException.class.isAssignableFrom(e.getClass()) ||
                com.google.gson.JsonParseException.class.isAssignableFrom(e.getClass()) ||
                com.google.gson.JsonSyntaxException.class.isAssignableFrom(e.getClass()) ||
                org.json.JSONException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Cons.Error.Internal, s);
            return true;
        }
        return false;
    }
}
