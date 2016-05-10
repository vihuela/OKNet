package ricky.oknet.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json printer helper
 */
public class JsonPrinter {

    private static final char TOP_LEFT_CORNER = '[';
    private static final String DOUBLE_DIVIDER = "--------------------------------------------------";
    private static final char HORIZONTAL_DOUBLE_LINE = '|';
    private static final char BOTTOM_LEFT_CORNER = '[';
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final int CHUNK_SIZE = 4000;
    private static final int JSON_INDENT = 4;
    /**
     * log tag
     */
    public static String TAG = "lays";


    public static void json(String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
            }
        } catch (JSONException ignored) {

        }
    }

    private static void d(String message, Object... args) {
        log(message, args);
    }

    private static synchronized void log(String msg, Object... args) {

        String tag = TAG;
        String message = createMessage(msg, args);

        Log.d(tag, TOP_BORDER);

        byte[] bytes = message.getBytes();
        int length = bytes.length;
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            logContent(tag, new String(bytes, i, count));
        }
        Log.d(tag, BOTTOM_BORDER);
    }

    private static void logContent(String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            Log.d(tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private static String createMessage(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

}
