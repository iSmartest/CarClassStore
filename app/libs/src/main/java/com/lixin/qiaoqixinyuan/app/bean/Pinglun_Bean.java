package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

import static com.lixin.qiaoqixinyuan.R.mipmap.huifu;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Pinglun_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String totalPage;//"5" //总页数
    public List<Messagelist> messagelist;//[{       //留言列表
        public class Messagelist{
            public String messageid;//"13"     //评论用户id
            public String messageusericon;//"http;////gisbdi"    //评论用户头像
            public String messageusertime;//"2016-11-13"   //评论时间
            public String messageusertext;//"味道不错"   //评论内容
            public String messagetitle;//:"昵称"    //用户昵称
            public List<ObtainselfdataBean.Obtainselfdata.MydataImagesBean> messageimage;//[{       //留言列表
            public List<Huifu> huifu;//[{       //留言列表
            public class Huifu{
                public String huifutext;//:“商家回复用户:”     //回复内容
        }

    }

}
