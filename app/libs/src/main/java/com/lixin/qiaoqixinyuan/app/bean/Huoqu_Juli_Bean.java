package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class Huoqu_Juli_Bean {
    public String result;//:"0" //0成功 1失败
    public String resultNote;//:”失败原因”
    public List<Fujinlist> fujinlist;//:[{          //附近距离列表
        public class Fujinlist{
            public String fujinId;//:“12”          //附近距离id
            public String fujinname;//:"300"     //附近距离
        }
}
