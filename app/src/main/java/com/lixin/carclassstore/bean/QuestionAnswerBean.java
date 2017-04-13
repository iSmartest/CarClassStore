package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 */

public class QuestionAnswerBean {
    public String result;//"0" 0成功1失败
    public String resultNote;//"失败原因"
    public String totalPage;//5//总页数
    public List<consults> consults;

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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<QuestionAnswerBean.consults> getConsults() {
        return consults;
    }

    public void setConsults(List<QuestionAnswerBean.consults> consults) {
        this.consults = consults;
    }

    public class consults{
        public String questionid;//"12" 问题id用于查看评论和提交评论时用
        public String userIcon;//"http://sdsad.png" //用户的头像
        public String userName;//"详情" //用户的名字
        public String userTalk;//"附近撒娇的撒旦就？" //用户询问的问题
        public String questionReplyNum;//"1212" //问题的回复数
        public String talkTime;//"2014-2-23" //发问的时间

        public String getQuestionid() {
            return questionid;
        }

        public void setQuestionid(String questionid) {
            this.questionid = questionid;
        }

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

        public String getQuestionReplyNum() {
            return questionReplyNum;
        }

        public void setQuestionReplyNum(String questionReplyNum) {
            this.questionReplyNum = questionReplyNum;
        }

        public String getTalkTime() {
            return talkTime;
        }

        public void setTalkTime(String talkTime) {
            this.talkTime = talkTime;
        }
    }
}
