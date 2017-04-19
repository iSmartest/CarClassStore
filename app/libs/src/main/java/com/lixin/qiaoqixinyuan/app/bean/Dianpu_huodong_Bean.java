package com.lixin.qiaoqixinyuan.app.bean;

import com.zhy.http.okhttp.utils.L;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Dianpu_huodong_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public String totalPage;//:"5" //总页数
    public List<Home_Bean.Huodongmodel> huodongmodel;

}
