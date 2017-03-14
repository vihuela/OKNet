package ricky.oknet.request;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ricky.oknet.model.HttpParams;

public interface HasBody<R> {

    R isMultipart(boolean isMultipart);

    R requestBody(RequestBody requestBody);

    R params(String key, File file);

    R addFileParams(String key, List<File> files);

    R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers);

    R params(String key, File file, String fileName);

    R params(String key, File file, String fileName, MediaType contentType);

    R upString(String string);

    R upJson(String json);

    R upJson(JSONObject jsonObject);

    R upJson(JSONArray jsonArray);

    R upBytes(byte[] bs);
}