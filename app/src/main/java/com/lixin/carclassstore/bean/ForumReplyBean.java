package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class ForumReplyBean {
    public String result;//"0" 0成功1失败
    public String resultNote;//"失败原因"
    public String totalPage;//5//总页数
    public List<replys> replys;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public List<ForumReplyBean.replys> getReplys() {
        return replys;
    }

    public void setReplys(List<ForumReplyBean.replys> replys) {
        this.replys = replys;
    }

    public class replys{
        public String userIcon;//"http://sdsad.png"
        public String userName;//"详情"
        public String userTalk;//"附近撒娇的撒旦就？"回复话
        public String talkTime;//"2014-2-23" //回复时间

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserTalk() {
            return userTalk;
        }

        public void setUserTalk(String userTalk) {
            this.userTalk = userTalk;
        }

        public String getTalkTime() {
            return talkTime;
        }

        public void setTalkTime(String talkTime) {
            this.talkTime = talkTime;
        }
    }
}
