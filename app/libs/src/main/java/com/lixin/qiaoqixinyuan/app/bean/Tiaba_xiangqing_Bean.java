package com.lixin.qiaoqixinyuan.app.bean;

import android.media.Image;
import android.provider.MediaStore;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class Tiaba_xiangqing_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public Tiebadetail tiebadetail;
   /* public List<Pinglun_Bean.Messagelist> pinglunlist;*/
    public class Tiebadetail
     {
         public String tiebauid;//"12"   //贴吧用户id
         public String nickName;
         public String tiebaicon;//"http;////bceodbwfc"  //贴吧用户头像
         public String tiebatime;//"2016-02-06"    //贴吧发布时间
         public String tiebatext;//"蛋糕"    //贴吧文字内容
         public String pinglunnum;//"101"   //评论数量
         public List<MyImageList> imagesList;
         public class MyImageList{
             public String imageUrl;// 'http;////sadsfsd'       // 图片
         }
    }
}
