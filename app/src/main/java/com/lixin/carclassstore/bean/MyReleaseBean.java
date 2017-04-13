package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/31
 * My mailbox is 1403241630@qq.com
 */

public class MyReleaseBean {

    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public List<qusetions> qusetions;
    private String totalPage;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public List<MyReleaseBean.qusetions> getQusetions() {
        return qusetions;
    }

    public void setQusetions(List<MyReleaseBean.qusetions> qusetions) {
        this.qusetions = qusetions;
    }

    public class qusetions{
        public String qusetionid;//""//发问题的id
        public String userTalk;//"附近撒娇的撒旦就？"//用户提交的文问题
        public String userTalkTime;//""///用户提交的文问题的时间
        public int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getQusetionid() {
            return qusetionid;
        }

        public void setQusetionid(String qusetionid) {
            this.qusetionid = qusetionid;
        }

        public String getUserTalk() {
            return userTalk;
        }

        public void setUserTalk(String userTalk) {
            this.userTalk = userTalk;
        }

        public String getUserTalkTime() {
            return userTalkTime;
        }

        public void setUserTalkTime(String userTalkTime) {
            this.userTalkTime = userTalkTime;
        }
    }
}
