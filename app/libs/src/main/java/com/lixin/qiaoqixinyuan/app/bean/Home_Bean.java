package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Home_Bean {
        public String result;  // 0成功 1失败
        public String resultNote;//: '失败原因'
        public List<ImagesList> imagesList;
        public List<Shouyemodel> shouyemodel;
        public List<Huodongmodel> huodongmodel;
        public List<Shangjiamodel> shangjiamodel;
        public List<Tiebamodel> tiebamodel;
      public class ImagesList{
          public String imageId;//: "12"                 // 商家id
          public String imageUrl;//: 'http://sadsfsd'       // 图片
          public String imageName;//:"酒"              //商家名称
      }
    class Shouyemodel{
        public String shouyemodelid;//: '1'          //首页第二模块id
        public String shouyemodelname;//:‘美食’               //分类的名称
    }
     public class Huodongmodel{
         public String shangjiaid;//:"35"              //商家id
         public String huodongid;//: '1'                    //活动id
         public String huodongimage;//: “http://yuodbuedb”    //活动图片
         public String huodongdescribe;//:“走过路过不要错过”    //活动描述
         public String huodongtime;//:“3天”               //活动时间
         public String huodongtitle;//:“叨叨”    //活动名称
         public String huodongnum;//:"12"    //活动点击量
         public String huodongtype;//:"0" //0距离活动结束时间 1距离活动开始 2活动已结束
     }
    public class Shangjiamodel{
        public String shangjiaid;//: '1'                    //商家id
        public String shangjiaimage;//: “http://yuodbuedb”    //商品图片
        public String shangjiajuli;//:“680m”       //  商家距离
        public String shangjiascribe;//:“走过路过不要错过”    //商品描述
        public String biaoqianimage;//:"http://hy9obniih"    //商家标签
        public String shangjianame;//:"多乐滋蛋糕"      //商家名称
        public String yuanprice;//:“58”               //商品原价
        public String xianprice;//:"78"       //商品现价
        public String huodongtype;//:"0"   //  0 全场活动  1 没有全部活动
        public String activityother;//:"0" // 0 满多少减多少 1 全场打5折  不选传-1
        public String activityfullreduction;//:"100-20"   //满100减20   不选传0
        public String shangpinname;//:"多乐滋蛋糕"     //商品名字
        public String shangjiaicon;//:"78"       //商家图片
    }
   public class Tiebamodel{
       public String tiebaId;//: '1'                    //贴吧id
       public String tiebaimage;//: “http://yuodbuedb”    //贴吧图片
       public String tiebascribe;//:“走过路过不要错过”    //贴吧描述
       public String tiebauserid;//:“10”               //发帖用户id
       public String tiebausername;//:"昵称"       //发帖用户名字
       public String tiebatime;//:"2016-02-01"  //发帖时间
       public String dianjiliang;//:"12"   //点击数量
       public String pinglunliang;//:"45"   //评论数量
       public String tiebaicon;//:"http://yuodbuedb"    //发帖人头像
       public String tiebatitle;//:"沁源"   //帖子标题
   }
}
