package ricky.oknet.utils;

/**
 * Created by ricky.yao on 2016/3/12
 */

public enum Error {
    NetWork,//net not response or timeout etc 无网、超时等
    Internal,//json parse error etc 一般是内部数据解析异常
    Server,//404.500 ..etc
    UnKnow,
    Invalid//request success but no need//token异常等自定义异常，可自行配置
}
