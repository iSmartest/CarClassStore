package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class MineMenuBean {

    public String result;//"0" 0成功1失败
    public String resultNote;//"失败原因"
    public String remmondCode;//@""//推荐有礼
    public List<messageMenu> messageMenu;
    public List<hpleMenu> hpleMenu;
    public List<aboutMenu> aboutMenu;

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

    public String getRemmondCode() {
        return remmondCode;
    }

    public void setRemmondCode(String remmondCode) {
        this.remmondCode = remmondCode;
    }

    public List<MineMenuBean.messageMenu> getMessageMenu() {
        return messageMenu;
    }

    public void setMessageMenu(List<MineMenuBean.messageMenu> messageMenu) {
        this.messageMenu = messageMenu;
    }

    public List<MineMenuBean.hpleMenu> getHpleMenu() {
        return hpleMenu;
    }

    public void setHpleMenu(List<MineMenuBean.hpleMenu> hpleMenu) {
        this.hpleMenu = hpleMenu;
    }

    public List<MineMenuBean.aboutMenu> getAboutMenu() {
        return aboutMenu;
    }

    public void setAboutMenu(List<MineMenuBean.aboutMenu> aboutMenu) {
        this.aboutMenu = aboutMenu;
    }

    public class messageMenu{
        public String meunType;//"记录仪"//站内信
        public String meunid;//""

        public String getMeunType() {
            return meunType;
        }

        public void setMeunType(String meunType) {
            this.meunType = meunType;
        }

        public String getMeunid() {
            return meunid;
        }

        public void setMeunid(String meunid) {
            this.meunid = meunid;
        }
    }

    public class hpleMenu{
        public String meunType;//"记录仪"
        public String meunid;//""

        public String getMeunType() {
            return meunType;
        }

        public void setMeunType(String meunType) {
            this.meunType = meunType;
        }

        public String getMeunid() {
            return meunid;
        }

        public void setMeunid(String meunid) {
            this.meunid = meunid;
        }
    }

    public class aboutMenu{
        public String meunType;//"记录仪"//关于我们
        public String meunUrl;//""//
        public String meunid;//""

        public String getMeunType() {
            return meunType;
        }

        public void setMeunType(String meunType) {
            this.meunType = meunType;
        }

        public String getMeunUrl() {
            return meunUrl;
        }

        public void setMeunUrl(String meunUrl) {
            this.meunUrl = meunUrl;
        }

        public String getMeunid() {
            return meunid;
        }

        public void setMeunid(String meunid) {
            this.meunid = meunid;
        }
    }
}

