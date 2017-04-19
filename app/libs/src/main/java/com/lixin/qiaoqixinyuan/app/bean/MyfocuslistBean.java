package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyfocuslistBean
 * 类描述：我的关注实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/13 18:01
 */

public class MyfocuslistBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public List<MyfocusBean> myfocuslist;

}
