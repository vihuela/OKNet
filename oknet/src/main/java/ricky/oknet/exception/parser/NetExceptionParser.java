package ricky.oknet.exception.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import ricky.oknet.utils.Cons;
import ricky.oknet.utils.Error;

class NetExceptionParser extends ExceptionParser {


    @Override
    protected boolean handler(@NonNull Throwable e, @NonNull IHandler handler) {
        String s = getMessageFromThrowable(e);
        if (Cons.IO_EXCEPTION.equalsIgnoreCase(s) || Cons.SOCKET_EXCEPTION.equalsIgnoreCase(s)) {
            //cancel request
            return true;
        }
        if (UnknownHostException.class.isAssignableFrom(e.getClass()) ||
                SocketException.class.isAssignableFrom(e.getClass()) ||
                SocketTimeoutException.class.isAssignableFrom(e.getClass())) {
            handler.onHandler(Error.NetWork, s);
            return true;
        }
        return false;
    }
}

