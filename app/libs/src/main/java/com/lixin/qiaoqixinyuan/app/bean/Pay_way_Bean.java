package com.lixin.qiaoqixinyuan.app.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class Pay_way_Bean implements Serializable {
    public String result;//"0" //0成功 1失败
    public String resultNote;//”失败原因”
    public String shangjiaphone;//"12365236548"   //商家电话
    public String shangjiaaddress;//"美林河畔"    //商家地址
    public String shangjiaprice;//"58"   //运费
    public String shangpinway;//"0"         //商家消费类型  0 送货上门 1 到店消费 2 送货上门+到店消费
    public String freightid;//"0" // 0 运费设置id /送货费几元，满多少免运费  freightid;//"1" // 1 满多少起送
    public String freightreduction;//"80"  //满80免运费     /freightid;//"1" 满80起送
}
