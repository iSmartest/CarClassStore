package com.lixin.carclassstore.bean;


import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/17
 * My mailbox is 1403241630@qq.com
 */

public class ContentBean {
    public String result;
    public String resultNote;
    public String totalPage;
    public List<checkServes> checkServes;
    public class checkServes{}
    public List<filtrate> filtrate;
    public class filtrate{}
    public List<rotateAdvertisement> rotateAdvertisement;
    public class rotateAdvertisement{}
    public List<serveBottom> serveBottom;
    public class serveBottom{}
    public List<commoditysList> commoditysList;
    public class commoditysList{
        public String goodid;
        public String isSeckill;
        public String seckillZone;
        public String sectionName;
        public List<commoditys>commoditys;
        public class commoditys{
            public String commodityBrandid;
            public String commodityDescription;
            public String commodityIcon;
            public String commodityNewPrice;
            public String commodityOriginalPrice;
            public String commodityShopName;
            public String commodityShopid;
            public String commodityTitle;
            public String commodityType;
            public String commodityid;
        }
    }
}
