# OKNet

> 在维护Rx支持时发现了实现良好的[okhttp-OkGo](https://github.com/jeasonlzy/okhttp-OkGo "OkGo")，作者很优秀，本着不浪费轮子的精神，将该库一些之前的特性与okhttp-OkGo结合便产生了该库，在此感谢okhttp-OkGo的作者。


- 完全兼容[okhttp-OkGo](https://github.com/jeasonlzy/okhttp-OkGo "OkGo")所有用法
- Rx与Callback两种api写法统一，完善的动态代理支持
- 独立完善的错误分发机制，清晰且容易定制的自定义异常，再也无须手动判断Exception对象
- 支持网络请求与页面绑定，网络队列可写于页面Base类中，网络请求随着页面销毁而取消
- JSON请求下支持参数拦截
- 支持Url占位符替换



----------
### Api ###

    @GET("method")
    @HEADER(key = "header_key", value = "header_val")
    @CACHE(CacheMode.FIRST_CACHE_THEN_REQUEST)
    Net<Request.Res> get(@Param("param1") String param1, @Param("param2") int param2);

    @POST("method")
    Net<Request.Res> post(@Param("param1") String param1);

    @GET("cache")
    @CACHE(CacheMode.REQUEST_FAILED_READ_CACHE)
    @CacheTime(10 * 1000)
    Net<Request.Res> cache();

    @JSON("uploadString")
    Net<Request.Res> json(@JsonParam Request.Req req);

    @STRING("uploadString")
    Net<Request.Res> string(@StringParam String req);

    @GET("download")
    @HEADER(key = "header_key", value = "header_val")
    Net fileDown(@Param("param") String param1);

    @POST("upload")
    @HEADER(key = "header_key", value = "header_val")
    Net<Request.Res> fileUpload(@Param("nick") String nick, @Param("avatar1") File avatar1);

	@GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/{size}/{page}")
    Net<Request.Res2> imageList(@Path("size") int size, @Path("page") int page);


### Usage ###
> 详细请下载Demo或直接查看[MainActivity](https://github.com/vihuela/OKNet/blob/master/app/src/main/java/ricky/oknets/MainActivity.java "MainActivity")

----------

> CallBack方式

    Api.getApi().get("param1", 2).execute(new JsonCallback<Request.Res>() {

            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                System.out.println();
            }

        }, mQueue);

> Rx方式

    Api.getApi().get("param1", 2).rx(new JsonConvert<Request.Res>() {
        }, new JsonCallback<Request.Res>() {
            @Override
            public void success(Request.Res res, boolean fromCache) {
                System.out.println();
            }

            @Override
            public void error(Error error, String message) {
                super.error(error, message);
            }
        }, mQueue);


### Demo ###

>请各个回调打上断点调试


![](http://i.imgur.com/1GnKtmG.png)


### Use ###


	<dependency>
	  <groupId>com.ricky</groupId>
	  <artifactId>oknet</artifactId>
	  <version>1.2.4</version>
	  <type>pom</type>
	</dependency>

	Or

	compile 'com.ricky:oknet:1.2.4'


### Proguard ###
[https://github.com/vihuela/OKNet/blob/master/app/proguard-rules.pro](https://github.com/vihuela/OKNet/blob/master/app/proguard-rules.pro "混淆")


