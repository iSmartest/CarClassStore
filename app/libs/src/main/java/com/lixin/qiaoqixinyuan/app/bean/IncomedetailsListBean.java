package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：IncomedetailsListBean
 * 类描述：收入明细列表实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/14 16:55
 */

public class IncomedetailsListBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public List<incomedetailsBean>incomedetailsList;
    public class incomedetailsBean{
        public String incometype;//0 提现  1 退款
        public String incometitle;// 0 提现-微信  1 退款-商店
        public String incometime;//明细时间
        public String incomenum;//消费金额
        public String incomebalance;//用户余额
    }
}
