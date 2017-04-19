package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/7
 * My mailbox is 1403241630@qq.com
 */

public class JavaBean {
    public String result;
    public String resultNote;
    public List<serveTop> serveTop;
    public List<rotateAdvertisement> rotateAdvertisement;
    public List<serveBottom> serveBottom;
    public List<filtrate> filtrate;
    public CheckAdvertisement checkAdvertisement;
    public CheckAdvertisement getCheckAdvertisement() {
            return checkAdvertisement;
        }
    public void setCheckAdvertisement(CheckAdvertisement checkAdvertisement) {
            this.checkAdvertisement = checkAdvertisement;
        }
    public class filtrate {
            public String filtrateTitle;
            public String filtrateid;
            public String getFiltrateTitle() {
                return filtrateTitle;
            }

            public void setFiltrateTitle(String filtrateTitle) {
                this.filtrateTitle = filtrateTitle;
            }

            public String getFiltrateid() {
                return filtrateid;
            }

            public void setFiltrateid(String filtrateid) {
                this.filtrateid = filtrateid;
            }
        }
    public class CheckAdvertisement{
            public String serveType;//跳转到商城列表时用
            public String serveIcon; //服务的头标
            public String serveTypeId;//商品类别id
            public List<checkServes> checkServes;

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

            public class checkServes{
                private String serveIcon; //服务的头标
                private String serveType; //服务的标题
                private String serveTypeId;//商品类别id
                private String serveDetailTitle; //服务描述"优质服务"

                public String getServeIcon() {
                    return serveIcon;
                }

                public void setServeIcon(String serveIcon) {
                    this.serveIcon = serveIcon;
                }

                public String getServeType() {
                    return serveType;
                }

                public void setServeType(String serveType) {
                    this.serveType = serveType;
                }

                public String getServeTypeId() {
                    return serveTypeId;
                }

                public void setServeTypeId(String serveTypeId) {
                    this.serveTypeId = serveTypeId;
                }

                public String getServeDetailTitle() {
                    return serveDetailTitle;
                }

                public void setServeDetailTitle(String serveDetailTitle) {
                    this.serveDetailTitle = serveDetailTitle;
                }
            }
        }
    public class serveBottom{
        public String serveIcon; //服务的头标
        public String serveType; //服务的标题，跳转到商城列表时用
        public String serveTypeId;//商品类别id

            public String getServeIcon() {
                return serveIcon;
            }

            public void setServeIcon(String serveIcon) {
                this.serveIcon = serveIcon;
            }

            public String getServeType() {
                return serveType;
            }

            public void setServeType(String serveType) {
                this.serveType = serveType;
            }

            public String getServeTypeId() {
                return serveTypeId;
            }

            public void setServeTypeId(String serveTypeId) {
                this.serveTypeId = serveTypeId;
            }
        }
    public class rotateAdvertisement{
        public String serveType;
        public String serveIcon;
        public String serveTypeId;

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
        }
    public class serveTop{
        private String serveIcon;
        private String serveType;
        private String serveTypeId;
        private String serveDetailTitle;

            public String getServeIcon() {
                return serveIcon;
            }

            public void setServeIcon(String serveIcon) {
                this.serveIcon = serveIcon;
            }

            public String getServeType() {
                return serveType;
            }

            public void setServeType(String serveType) {
                this.serveType = serveType;
            }

            public String getServeTypeId() {
                return serveTypeId;
            }

            public void setServeTypeId(String serveTypeId) {
                this.serveTypeId = serveTypeId;
            }

            public String getServeDetailTitle() {
                return serveDetailTitle;
            }

            public void setServeDetailTitle(String serveDetailTitle) {
                this.serveDetailTitle = serveDetailTitle;
            }
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

}


