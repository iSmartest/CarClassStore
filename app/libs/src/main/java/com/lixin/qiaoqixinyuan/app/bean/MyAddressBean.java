package com.lixin.qiaoqixinyuan.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyAddressBean
 * 类描述：我的收货地址实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/14 11:43
 */

public class MyAddressBean{
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public List<AddressBean>addressList;
    public class AddressBean{
        public String addressId;//收货地址ID
        public String address;//地址加上详细地址
        public String latitude;//客户当前纬度
        public String longitude;//客户当前经度
        public String userPhone;//收货人电话
        public String userName;//收货人姓名
        public String cityId;//城市Id
        public String isDefault;//是默认地址false不是默认地址 一个用户最多只能有一个默认地址
    }
}
