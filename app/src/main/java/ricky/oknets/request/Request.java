package ricky.oknets.request;

import java.io.Serializable;
import java.util.List;

import ricky.oknet.utils.INoProguard;
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

    public static class Res1 extends CommonResponse {


        public int data;


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

    public static class Res2 implements INoProguard {


        public boolean error;
        public List<Item> results;

        static class Item implements INoProguard {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String source;
            public String type;
            public String url;
            public boolean used;
            public String who;
        }


    }
    public static class Res3 implements INoProguard {


        public String date;
        public List<StoriesBean> stories;
        public List<TopStoriesBean> top_stories;

        public static class StoriesBean implements INoProguard {
            public String ga_prefix;
            public int id;
            public String title;
            public int type;
            public boolean multipic;
            public List<String> images;
        }

        public static class TopStoriesBean implements INoProguard {
            public String ga_prefix;
            public int id;
            public String image;
            public String title;
            public int type;
        }
    }
}
