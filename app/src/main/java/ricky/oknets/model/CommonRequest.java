package ricky.oknets.model;

/**
 * @author ricky.yao on 2016/6/3.
 */
public class CommonRequest {
    public String token;
    public CheckSignBean checkSign;

    public static class CheckSignBean {
        public String mac;
        public String time;
        public String version;
        public String sign;
        public int appVersion;
    }
}
