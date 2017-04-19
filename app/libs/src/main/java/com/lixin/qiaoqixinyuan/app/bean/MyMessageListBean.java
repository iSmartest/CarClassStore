package com.lixin.qiaoqixinyuan.app.bean;

import java.util.List;

/**
 * 项目名称：QiaoQiXinYuan
 * 类名称：MyMessageListBean
 * 类描述：我的消息实体类
 * 创建人：Tiramisu
 * 创建时间：2017/2/14 15:46
 */

public class MyMessageListBean {
    public String result;//0成功 1失败
    public String resultNote;//失败原因
    public String totalPage;//总页数
    public String newMessageNum;//未读消息数
    public List<MessageBean> messageList;

    public class MessageBean {
        public String messageId;//消息id
        public String type;//0未读 1已读
        public String messageTime;//消息时间
        public String messageContent;//消息内容
    }
}
