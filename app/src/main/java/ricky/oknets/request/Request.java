package ricky.oknets.request;

import java.io.Serializable;

import ricky.oknets.common.CommonRequest;
import ricky.oknets.common.CommonResponse;

public class Request {
    public static class Req extends CommonRequest {
        public long userId = 50087;
        public ReqItemClass reqItemClass = new ReqItemClass();

        public static class ReqItemClass {
            public String name = "ricky";
            public String sex = "man";
        }
    }

    public static class Res extends CommonResponse {


        public DataBean data;


        public static class DataBean implements Serializable {

            public AuthorBean author;
            public String des;
            public String method;
            public String url;
            public String ip;

            public static class AuthorBean implements Serializable {

                public String des;
                public String email;
                public String address;
                public String name;
                public String github;
                public String qq;
                public String fullname;
            }
        }
    }
}
