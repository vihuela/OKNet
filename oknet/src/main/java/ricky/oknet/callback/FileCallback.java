package ricky.oknet.callback;


import java.io.File;

import okhttp3.Response;
import ricky.oknet.convert.FileConvert;

public abstract class FileCallback extends AbsCallback<File> {

    private FileConvert convert;    //文件转换类

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertSuccess(Response response) throws Exception {
        File file = convert.convertSuccess(response);
        response.close();
        return file;
    }

}