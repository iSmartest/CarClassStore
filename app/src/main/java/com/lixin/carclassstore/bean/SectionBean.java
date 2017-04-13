package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class SectionBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public List<forumSections> forumSections;//"失败原因"

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

    public List<SectionBean.forumSections> getForumSections() {
        return forumSections;
    }

    public void setForumSections(List<SectionBean.forumSections> forumSections) {
        this.forumSections = forumSections;
    }

    public class forumSections{
        public String sectionTile;//"买车宝典"
        public String plateid;//""版块id，用于请求列表新闻时用

        public String getSectionTile() {
            return sectionTile;
        }

        public void setSectionTile(String sectionTile) {
            this.sectionTile = sectionTile;
        }

        public String getPlateid() {
            return plateid;
        }

        public void setPlateid(String plateid) {
            this.plateid = plateid;
        }
    }
}
