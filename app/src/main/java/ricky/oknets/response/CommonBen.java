package ricky.oknets.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author ricky.yao on 2016/6/2.
 */
public class CommonBen implements Serializable {


    public String name;
    public String logo;
    public int id;
    public int userId;
    public String orderid;
    public String content;
    public String images;
    public int rating;
    public int comUser;
    public int createAt;
    public int size;
    public String message;
    public int status;
    public List<List<ItemBean>> item;

    public static class ItemBean {
        public String name;
        public String logo;
        public int id;
        public int userId;
        public String orderid;
        public String content;
        public String images;
        public int rating;
        public int comUser;
        public int createAt;
        public int size;
    }
}
