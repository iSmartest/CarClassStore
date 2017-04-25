package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class OpinionBean {
    public String result;//"0" //0成功1失败
    public String resultNote;
    public String totalPage;
    public List<commodityCommentLists> commodityCommentLists;

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

    public List<OpinionBean.commodityCommentLists> getCommodityCommentLists() {
        return commodityCommentLists;
    }

    public void setCommodityCommentLists(List<OpinionBean.commodityCommentLists> commodityCommentLists) {
        this.commodityCommentLists = commodityCommentLists;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public class commodityCommentLists{
        public String userIcon;
        public String userName;
        public String userComment;
        public String commentTime;
        public String buyTime;
        public String commentReply;
        public String commentStarNum;
        public List<String> commentPics;

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserComment() {
            return userComment;
        }

        public void setUserComment(String userComment) {
            this.userComment = userComment;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public String getCommentReply() {
            return commentReply;
        }

        public void setCommentReply(String commentReply) {
            this.commentReply = commentReply;
        }

        public String getCommentStarNum() {
            return commentStarNum;
        }

        public void setCommentStarNum(String commentStarNum) {
            this.commentStarNum = commentStarNum;
        }

        public List<String> getCommentPics() {
            return commentPics;
        }

        public void setCommentPics(List<String> commentPics) {
            this.commentPics = commentPics;
        }
    }
}
