package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Geren_zhuye_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String personalage;//"18"    //年龄
    public String personalsex;//"女"   //性别
    public String personalsignature;//"不错"    //个性签名
    public String personalfocus;//"23"      //关注的人
    public String personalfensi;//"23"      //粉丝
    public String focustype;//"0"      //0 已关注   1 取消关注
    public String blacktype;//:"0"      //0 未加入黑名单   1 加入黑名单
    public String note;//"好的"    //备注
    public List<Personalimage> personalimage;//[{      // 用户图片
    public class Personalimage{
        public String imageId;// "12"          // 图片id
        public String imageUrl;// 'http;////sadsfsd'       // 图片
    }
}
