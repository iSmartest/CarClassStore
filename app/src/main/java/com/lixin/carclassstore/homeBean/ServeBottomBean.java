package com.lixin.carclassstore.homeBean;

/**
 * Created by 小火
 * Create time on  2017/4/5
 * My mailbox is 1403241630@qq.com
 */

public class ServeBottomBean {

        String serveIcon;//服务的头标 "http://sds.png"
        String serveType; //服务的标题，跳转到商城列表时用 "换轮胎"
        String serveTypeId; //商品类别id

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
        @Override
        public String toString() {
            return "ServeBottomBean [serveIcon=" + serveIcon + ", serveType="
                    + serveType + ", serveTypeId=" + serveTypeId + "]";
        }
    }

