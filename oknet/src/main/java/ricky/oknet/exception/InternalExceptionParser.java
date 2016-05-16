package ricky.oknet.exception;

import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import ricky.oknet.utils.Cons;

public class InternalExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(Throwable e, IHandler handler) {
        if (e != null) {
            String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

            if (NumberFormatException.class.isAssignableFrom(e.getClass()) ||
                    JsonParseException.class.isAssignableFrom(e.getClass()) ||
                    JSONException.class.isAssignableFrom(e.getClass())) {
                handler.onHandler(Cons.Error.Internal, s);
                return true;
            }
        }
        return false;
    }
}
