package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 */

public class CarSeries {
    public String result;
    public String resultNote;
    public String totalPage;
    public List<carVersionsList> carVersionsList;

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

    public List<CarSeries.carVersionsList> getCarVersionsList() {
        return carVersionsList;
    }

    public void setCarVersionsList(List<CarSeries.carVersionsList> carVersionsList) {
        this.carVersionsList = carVersionsList;
    }

    public class carVersionsList{
        public String carVersionName;
        public String id;
        public List<getCarVersionInfo>getCarVersionInfo;

        public String getCarVersionName() {
            return carVersionName;
        }

        public void setCarVersionName(String carVersionName) {
            this.carVersionName = carVersionName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<CarSeries.carVersionsList.getCarVersionInfo> getGetCarVersionInfo() {
            return getCarVersionInfo;
        }

        public void setGetCarVersionInfo(List<CarSeries.carVersionsList.getCarVersionInfo> getCarVersionInfo) {
            this.getCarVersionInfo = getCarVersionInfo;
        }

        public class getCarVersionInfo{
            public String carIcon;
            public String carPriceZone;
            public String carVersionId;
            public String carVersionName;

            public String getCarIcon() {
                return carIcon;
            }

            public void setCarIcon(String carIcon) {
                this.carIcon = carIcon;
            }

            public String getCarPriceZone() {
                return carPriceZone;
            }

            public void setCarPriceZone(String carPriceZone) {
                this.carPriceZone = carPriceZone;
            }

            public String getCarVersionId() {
                return carVersionId;
            }

            public void setCarVersionId(String carVersionId) {
                this.carVersionId = carVersionId;
            }

            public String getCarVersionName() {
                return carVersionName;
            }

            public void setCarVersionName(String carVersionName) {
                this.carVersionName = carVersionName;
            }
        }
    }
}

