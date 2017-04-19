package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：ObtainselfdataBean
 * 类描述：获取个人资料实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/10 18:20
 */

public class ObtainselfdataBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public Obtainselfdata obtainselfdata;
    public class Obtainselfdata{
        public String mynick;//昵称
        public String mysex;//性别
        public String myage;//年龄
        public String mysignature;//性格开朗
        public List<MydataImagesBean>mydataImages;
        public class MydataImagesBean{
            public String mydataImage;//个人图片
        }
    }
}
