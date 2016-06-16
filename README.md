# OKNet

- GET、POST、HEAD、OPTIONS、PUT、DELETE
- POST文件上传(字段、文件混合接口)
- 文件下载进度回调
- Https，多种证书设置
- 取消某个请求
- 自定义Callback
- 支持重定向
- 完善的Log日志输出
- **完善的错误分发链，支持自定义添加错误解析器**
- 支持协议304缓存(解析Bean对象及其包含子对象需要implements Serializable)
- **支持Retrofit形式调用**
- Retrofit模式下，支持参数为json时通用参数拦截修改
- Retrofit模式下，支持view与网络绑定取消请求


----------
## in Application onCreate() ##

	 OkHttpUtils.getInstance()//
                .baseUrl("http://server.jeasonlzy.com/OkHttpUtils/")
                .debug(true, true, "OKNet")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)            //全局的写入超时时间
                /*.addCommonHeaders(headers)   */                                      //设置全局公共头
				// .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                   //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonParams(params);

## GET、POST ##

	OkHttpUtils.get(url)//post(url)
				.tag(this)//可在onDestroy() OkHttpUtils.getInstance().cancelTag(this);
                .execute(new DialogCallback<CityResponse.DataBean>(this) {
                    @Override
                    public void onSimpleError(Cons.Error error, String message) {
                        System.out.println();
                    }

                    @Override
                    public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {
                        System.out.println();
                    }
                });

## POST File and Text ##

    OkHttpUtils.post(url)
				.tag(this)
                .headers("h1","h1")
                .params("p1","p1")
                .params("p2",file)//file
                .execute(new DialogCallback<CityResponse.DataBean>(this) {
                    @Override
                    public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {
                        
                    }

                    @Override
                    public void onSimpleError(Cons.Error error, String message) {

                    }
					//上传回调
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                    }
                });
## DownLoad File ##
    OkHttpUtils.get(url)
				.tag(this)
                .execute(new DialogCallback<CityResponse.DataBean>(this) {
                    @Override
                    public void onResponse(boolean isFromCache, CityResponse.DataBean dataBean, Request request, @Nullable Response response) {

                    }

                    @Override
                    public void onSimpleError(Cons.Error error, String message) {

                    }
					//下载回调
                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                    }
                });

## Bitmap File ##

    OkHttpUtils.get(url)//
                .tag(this)//
                .execute(new BitmapDialogCallback(this) {
                    @Override
                    public void onResponse(boolean isFromCache, Bitmap bitmap, Request request, Response response) {
                        handleResponse(isFromCache, bitmap, request, response);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(isFromCache, call, response, e);
                        handleError(isFromCache, call, response);
                    }
                });

## Https ##

	OkHttpUtils.get("https://kyfw.12306.cn/otn")//
                    .tag(this)//
                    .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                    .setCertificates(new Buffer().writeUtf8(CER_12306).inputStream())  //方法一：设置自签名网站的证书（选一种即可）
                    .setCertificates(getAssets().open("srca.cer"))                     //方法二：也可以设置https证书（选一种即可）
                    .setCertificates()                                                 //方法三：信任所有证书（选一种即可）
                    .execute(new HttpsCallBack(this));

## 自定义Callback ##

	public abstract class CommonCallback<T> extends AbsCallback<T>

## 自定义异常处理 ##
> 在AbsCallback类中有个addExceptionParser方法<br>
> 通常配合具体业务回调更精准的错误分发


	addExceptionParser(new ExceptionParser() {
            @Override
            protected boolean handler(Throwable e, IHandler handler) {

                if (e != null) {
                    String s = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();

                    if (JSONException.class.isAssignableFrom(e.getClass())) {
                        handler.onHandler(Cons.Error.NetWork, s);
                        return true;
                    }
                }
                return false;
            }
        });

## Log ##

    	05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet: Path：
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [RequestType：GET] 
		                                                       [Host：192.168.1.70] 
		                                                       [Port：80] 
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet: Headers：
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [Accept-Language：zh-CN,zh;q=0.8]
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [User-Agent：Mozilla/5.0 (Linux; U; Android 5.0; zh-cn; SM-G900F Build/LRX21T) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30]
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet: Params：
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [productLine：5]
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [os：android]
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [commonParamsKey2：这里支持中文参数]
		05-17 10:34:22.348 27745-27960/ricky.oknets E/OKNet:   [commonParamsKey1：commonParamsValue1]
		05-17 10:34:22.668 27745-27960/ricky.oknets E/OKNet: ---RES : [318.3ms] 
		05-17 10:34:22.668 27745-27960/ricky.oknets E/OKNet: [Protocol：http/1.1]  [Code：200]  [Message：OK]  [ContentType：application/json;charset=UTF-8]

## Retrofit形式调用 ##
	Api:
	@GET("http://192.168.1.70/api/common/cityList")//?productLine=5&os=android
    NetRequest<CityResponse.DataBean> cityList(@PARAMS("productLine") int productLine, @PARAMS("os") String os);

    @CACHE(CacheMode.FIRST_CACHE_THEN_REQUEST)
    @GET("method")
    NetRequest<RequestInfo> method();

	Invoke:
	ApiUtils.Instance.getApi().method().execute(new JsonCallback<RequestInfo>() {
            @Override
            public void onResponse(boolean isFromCache, RequestInfo requestInfo, Request request, @Nullable Response response) {
                System.out.println();
            }

            @Override
            public void onSimpleError(Cons.Error error, String message) {
                System.out.println();
            }
        });

    PostJson下拦截参数：
    public HttpApi getApi() {
            return ApiHelper.get(HttpApi.class/*, new NetUtil.ICustomerJsonBean<CommonRequest>() {
                @Override
                public CommonRequest onInterceptRequest(CommonRequest commonRequest) {
                    commonRequest.token = "123456";
                    CommonRequest.CheckSignBean checkSignBean = new CommonRequest.CheckSignBean();
                    checkSignBean.appVersion = 123;
                    checkSignBean.mac = "sdfskldfsld";
                    commonRequest.checkSign = checkSignBean;
                    return commonRequest;
                }
            }*/);
        }
    与View层绑定
    View的基类实现 implements INetViewLifecycle
    ApiUtils.Instance.getApi().method().bind(this).execute
    在需要的地方执行NetLifecycleMgr.Instance.onNetBehavior(this,OKNetBehavior.DESTROY);
-----------------

## Gradle ##
**compile 'com.ricky:oknet:1.2.2'**

## License ##
No Fucking License
> Source part Sources Online open source projects<br>
> thanks my brother [mingge](https://github.com/zhoumingliang "mingge")


