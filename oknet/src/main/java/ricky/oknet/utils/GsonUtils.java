package ricky.oknet.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * gson singleton
 */
public enum GsonUtils {
    INSTANCE;

    public final Gson gson;

    GsonUtils() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }
}
