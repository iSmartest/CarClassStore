package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/5
 * My mailbox is 1403241630@qq.com
 */

public class CheckAdvertisementBean {
    private String serveType;
    private String serveIcon;
    private String serveTypeId;
    private List<CheckServesBean> checkServes;

    class CheckServesBean {
        String serveIcon;
        String serveType;
        String serveTypeId;
        String serveDetailTitle;

        public String getServeIcon() {
            return serveIcon;
        }

        public void setServeIcon(String serveIcon) {
            this.serveIcon = serveIcon;
        }

        public String getServeTypeId() {
            return serveTypeId;
        }

        public void setServeTypeId(String serveTypeId) {
            this.serveTypeId = serveTypeId;
        }

        public String getServeType() {
            return serveType;
        }

        public void setServeType(String serveType) {
            this.serveType = serveType;
        }

        public String getServeDetailTitle() {
            return serveDetailTitle;
        }

        public void setServeDetailTitle(String serveDetailTitle) {
            this.serveDetailTitle = serveDetailTitle;
        }

        @Override
        public String toString() {
            return  "CheckServesBean [serveIcon=" + serveIcon + ", serveType="
                    + serveType + ", serveTypeId=" + serveTypeId + ", serveDetailTitle=" + serveDetailTitle
                    + "]";
        }
    }

    public List<CheckServesBean> getCheckServes() {
        return checkServes;
    }

    public void setCheckServes(List<CheckServesBean> checkServes) {
        this.checkServes = checkServes;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public String getServeIcon() {
        return serveIcon;
    }

    public void setServeIcon(String serveIcon) {
        this.serveIcon = serveIcon;
    }

    public String getServeTypeId() {
        return serveTypeId;
    }

    public void setServeTypeId(String serveTypeId) {
        this.serveTypeId = serveTypeId;
    }

    @Override
    public String toString() {
        return  "CheckAdvertisementBean [serveType=" + serveType + ", serveIcon="
                + serveIcon + ", serveTypeId=" + serveTypeId + ", checkServes=" + checkServes
                + "]";
    }
}
