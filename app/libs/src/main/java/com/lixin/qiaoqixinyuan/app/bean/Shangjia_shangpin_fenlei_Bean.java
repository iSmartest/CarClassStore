package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class Shangjia_shangpin_fenlei_Bean {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String shopSendMoney;//"3元"            //满20起送
    public String shangjiatimetype;//"0"   //0 营业时间内   1 未营业
    public List<Shangpinfenlei> shangpinfenlei;//[{          //商家商品分类列表
    public class Shangpinfenlei{
        public String fenleiId;// '1'                    //分类id
        public String fenleiname;//"多乐滋蛋糕"   //分类名称
    }
}
