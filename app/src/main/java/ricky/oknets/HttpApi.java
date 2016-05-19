package ricky.oknets;

import ricky.oknet.modeinterface.NetRequest;
import ricky.oknet.modeinterface.annotation.GET;
import ricky.oknet.modeinterface.annotation.PARAMS;

/************************************************************
 * Author:  Zhouml
 * Description:     // 模块描述
 * Date: 2016/5/18
 ************************************************************/
public interface HttpApi {
//    http://192.168.1.70/api/common/cityList?productLine=5&os=android

    @GET( "http://192.168.1.70/api/common/cityList")
    NetRequest<String> test(@PARAMS("productLine")int productLine,@PARAMS("os")String os);


}
