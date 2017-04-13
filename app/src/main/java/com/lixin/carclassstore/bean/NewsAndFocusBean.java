package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/11
 * My mailbox is 1403241630@qq.com
 */

public class NewsAndFocusBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public String totalPage;//5 总页数
    public List<newsList> newsList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<NewsAndFocusBean.newsList> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsAndFocusBean.newsList> newsList) {
        this.newsList = newsList;
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

    public class newsList{
            public String newid;//""新闻id 查新闻详情使用
            public String forumTitle;//"标题"  新闻的标题
            public String forumAttentionType;//"0"//0为已关注1为未关注
            public String forumDetail;//"详情描述" //新闻详情描述
            public String forumIcon;//"http://sdhajs.png"//新闻左图片
            public String forumTime;//"2017-03-2" //新闻的日期
            public String forumUrl;//"http://sdsadsad"//新闻详情链接

        public String getNewid() {
            return newid;
        }

        public void setNewid(String newid) {
            this.newid = newid;
        }

        public String getForumTitle() {
            return forumTitle;
        }

        public void setForumTitle(String forumTitle) {
            this.forumTitle = forumTitle;
        }

        public String getForumAttentionType() {
            return forumAttentionType;
        }

        public void setForumAttentionType(String forumAttentionType) {
            this.forumAttentionType = forumAttentionType;
        }

        public String getForumDetail() {
            return forumDetail;
        }

        public void setForumDetail(String forumDetail) {
            this.forumDetail = forumDetail;
        }

        public String getForumIcon() {
            return forumIcon;
        }

        public void setForumIcon(String forumIcon) {
            this.forumIcon = forumIcon;
        }

        public String getForumTime() {
            return forumTime;
        }

        public void setForumTime(String forumTime) {
            this.forumTime = forumTime;
        }

        public String getForumUrl() {
            return forumUrl;
        }

        public void setForumUrl(String forumUrl) {
            this.forumUrl = forumUrl;
        }
    }
}
