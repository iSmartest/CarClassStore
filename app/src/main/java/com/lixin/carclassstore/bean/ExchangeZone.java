package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/28
 * My mailbox is 1403241630@qq.com
 */

public class ExchangeZone {
    public String result;
    public String resultNote;
    public String totalPage;
    public List<commoditys> commoditys;

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

    public List<ExchangeZone.commoditys> getCommoditys() {
        return commoditys;
    }

    public void setCommoditys(List<ExchangeZone.commoditys> commoditys) {
        this.commoditys = commoditys;
    }

    public class commoditys{
        public String commodityid;
        public String commodityIcon;
        public String commodityTitle;
        public String commoditycreditsNum;

        public String getCommodityid() {
            return commodityid;
        }

        public void setCommodityid(String commodityid) {
            this.commodityid = commodityid;
        }

        public String getCommodityIcon() {
            return commodityIcon;
        }

        public void setCommodityIcon(String commodityIcon) {
            this.commodityIcon = commodityIcon;
        }

        public String getCommodityTitle() {
            return commodityTitle;
        }

        public void setCommodityTitle(String commodityTitle) {
            this.commodityTitle = commodityTitle;
        }

        public String getCommoditycreditsNum() {
            return commoditycreditsNum;
        }

        public void setCommoditycreditsNum(String commoditycreditsNum) {
            this.commoditycreditsNum = commoditycreditsNum;
        }
    }
}
