package ricky.oknet.retrofit;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ricky.oknet.cache.CacheEntity;
import ricky.oknet.cache.CacheMode;
import ricky.oknet.model.HttpParams;
import ricky.oknet.retrofit.anno.BYTES;
import ricky.oknet.retrofit.anno.BytesParam;
import ricky.oknet.retrofit.anno.CACHE;
import ricky.oknet.retrofit.anno.CacheTime;
import ricky.oknet.retrofit.anno.DELETE;
import ricky.oknet.retrofit.anno.GET;
import ricky.oknet.retrofit.anno.HEAD;
import ricky.oknet.retrofit.anno.HEADER;
import ricky.oknet.retrofit.anno.JSON;
import ricky.oknet.retrofit.anno.JsonParam;
import ricky.oknet.retrofit.anno.OPTIONS;
import ricky.oknet.retrofit.anno.POST;
import ricky.oknet.retrofit.anno.PUT;
import ricky.oknet.retrofit.anno.Param;
import ricky.oknet.retrofit.anno.STRING;
import ricky.oknet.retrofit.anno.StringParam;
import ricky.oknet.utils.DataConvert;

@SuppressWarnings("all")
public class ApiProxyHandler<F> implements InvocationHandler {

    private NetRequestData.HttpRequestType type;
    private CacheMode cacheMode;
    private long cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
    private NetRequestData.Header header;
    private String url;
    private Class<F> jsonClazz;
    private ICustomerJsonBean<F> iCustomerJsonBean;


    public ApiProxyHandler(ICustomerJsonBean<F> callback) {
        if (callback != null) {
            jsonClazz = (Class<F>) ((ParameterizedType) callback.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
            iCustomerJsonBean = callback;
        }
    }

    /**
     * 获取指定方法的参数名
     *
     * @param type
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    public static List<String> getMethodParameterNamesByAnnotation(Method method) {

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return new ArrayList<>();
        }
        List<String> parameteNames = new ArrayList<>();
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                //get post etc
                if (annotation instanceof Param) {
                    parameteNames.add(((Param) annotation).value());
                }
            }
        }
        return parameteNames;
    }

    @Override
    public Net invoke(Object proxy, final Method method, final Object[] args) throws Throwable {

        //method
        if (method.isAnnotationPresent(POST.class)) {
            url = method.getAnnotation(POST.class).value();
            type = NetRequestData.HttpRequestType.POST;
        } else if (method.isAnnotationPresent(GET.class)) {
            url = method.getAnnotation(GET.class).value();
            type = NetRequestData.HttpRequestType.GET;
        } else if (method.isAnnotationPresent(HEAD.class)) {
            url = method.getAnnotation(HEAD.class).value();
            type = NetRequestData.HttpRequestType.HEAD;
        } else if (method.isAnnotationPresent(OPTIONS.class)) {
            url = method.getAnnotation(OPTIONS.class).value();
            type = NetRequestData.HttpRequestType.OPTIONS;
        } else if (method.isAnnotationPresent(DELETE.class)) {
            url = method.getAnnotation(DELETE.class).value();
            type = NetRequestData.HttpRequestType.DELETE;
        } else if (method.isAnnotationPresent(PUT.class)) {
            url = method.getAnnotation(PUT.class).value();
            type = NetRequestData.HttpRequestType.PUT;
        } else if (method.isAnnotationPresent(JSON.class)) {
            url = method.getAnnotation(JSON.class).value();
            type = NetRequestData.HttpRequestType.JSON;
        } else if (method.isAnnotationPresent(BYTES.class)) {
            url = method.getAnnotation(BYTES.class).value();
            type = NetRequestData.HttpRequestType.BYTES;
        } else if (method.isAnnotationPresent(STRING.class)) {
            url = method.getAnnotation(STRING.class).value();
            type = NetRequestData.HttpRequestType.STRING;
        }

        //cache
        if (method.isAnnotationPresent(CACHE.class)) {
            cacheMode = method.getAnnotation(CACHE.class).value();
            if (method.isAnnotationPresent(CacheTime.class)) {
                cacheTime = method.getAnnotation(CacheTime.class).value();
            }
        }

        //header
        if (method.isAnnotationPresent(HEADER.class)) {
            header = new NetRequestData.Header();
            header.key = method.getAnnotation(HEADER.class).key();
            header.value = method.getAnnotation(HEADER.class).value();
        }

        return new Net(generateDataObject(method, args));
    }

    private Type[] getTType(Method method) {
        Type[] types = null;
        try {
            Type returnType = method.getGenericReturnType();// 返回类型
            types = ((ParameterizedType) returnType).getActualTypeArguments();
        } catch (GenericSignatureFormatError error) {
            error.printStackTrace();
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        } catch (MalformedParameterizedTypeException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
        }
        return types;
    }

    /**
     * 判断方法的参数是否有文件
     *
     * @param objects
     * @return
     */
    private NetRequestData.HttpRequestContent containFile(Object[] objects) {
        if (objects == null)
            return NetRequestData.HttpRequestContent.DEFAULT;
        for (Object obj : objects) {
            if (File.class.isInstance(obj)) {
                return NetRequestData.HttpRequestContent.FILE;
            }
        }
        return NetRequestData.HttpRequestContent.DEFAULT;
    }


    private NetRequestData generateDataObject(Method method, Object[] objs) {

        NetRequestData netRequestData = new NetRequestData();
        HttpParams httpParams = new HttpParams();

        netRequestData.header = header;
        netRequestData.url = url;
        netRequestData.type = type;
        netRequestData.cacheMode = cacheMode;
        netRequestData.cacheTime = cacheTime;
        netRequestData.methodName = method.getName();//tag

//        netRequestData.requestContent = containFile(objs);
//        netRequestData.types = getTType(method);

        switch (type) {
            case GET:
            case POST:
            case DELETE:
            case HEAD:
            case OPTIONS:
            case PUT:
                List<String> paramsName = getMethodParameterNamesByAnnotation(method);
                int i = 0;
                for (String name : paramsName) {
                    if (objs == null || objs[i] == null) {
                        //param is null
                        httpParams.put(name, "");
                    } else if (File.class.isInstance(objs[i])) {
                        httpParams.put(name, (File) objs[i]);
                    } else {
                        httpParams.put(name, objs[i].toString() + "");
                    }
                    i++;
                }
                netRequestData.params = httpParams;
                break;
            case JSON:
                checkValid(type, method, objs);
                if (objs != null && objs.length > 0 && objs.length == 1) {
                    Object target = objs[0];
                    if (iCustomerJsonBean != null) {
                        try {
                            target = iCustomerJsonBean.onInterceptRequest((F) target);
                        } catch (ClassCastException e) {
                            throw new IllegalArgumentException(target.getClass().getSimpleName() + " must extends " + jsonClazz.getSimpleName());
                        }
                    }
                    netRequestData.jsonParam = DataConvert.toJson(target);
                    break;
                }
                throw new IllegalArgumentException(method.getName() + " allow a object parameter under " + type.name() + " mode");
            case BYTES:
                checkValid(type, method, objs);
                if (objs != null && objs.length > 0 && objs.length == 1) {
                    Object target = objs[0];
                    netRequestData.byteParam = (byte[]) objs[0];
                    break;
                }
                throw new IllegalArgumentException(method.getName() + " allow a bytes parameter under " + type.name() + " mode");
            case STRING:
                checkValid(type, method, objs);
                if (objs != null && objs.length > 0 && objs.length == 1) {
                    Object target = objs[0];
                    netRequestData.stringParam = (String) target;
                    break;
                }
                throw new IllegalArgumentException(method.getName() + " allow a string parameter under " + type.name() + " mode");
        }

        return netRequestData;
    }

    private void checkValid(NetRequestData.HttpRequestType type, Method method, Object[] objs) {
        boolean res = false;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations.length == 1 && objs.length == 1) {
            try {

                Annotation annotation = parameterAnnotations[0][0];
                switch (type) {
                    case JSON:
                        if (annotation instanceof JsonParam) {
                            res = true;
                        }
                        break;
                    case STRING:
                        if (annotation instanceof StringParam) {
                            res = true;
                        }
                        break;
                    case BYTES:
                        if (annotation instanceof BytesParam) {
                            res = true;
                        }
                        break;
                }
            } catch (IndexOutOfBoundsException ignore) {

            }

        }
        if (!res) {
            throw new IllegalArgumentException("must use " + type + "Param anno under " + type + " mode");
        }

    }

    private byte[] bitmap2bytes(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 所有请求的基类参数类
     *
     * @param <F>
     */
    public static interface ICustomerJsonBean<F> {
        F onInterceptRequest(F f);
    }

}
