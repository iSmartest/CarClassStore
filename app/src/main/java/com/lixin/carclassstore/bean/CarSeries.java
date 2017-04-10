package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/3/27
 * My mailbox is 1403241630@qq.com
 */

public class CarSeries {
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

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public List<CarSeries.carVersionsList> getCarVersionsList() {
        return carVersionsList;
    }

    public void setCarVersionsList(List<CarSeries.carVersionsList> carVersionsList) {
        this.carVersionsList = carVersionsList;
    }

    public String result;
    public String resultNote;
    public String totalPage;
    public String carInfo;
    public List<carVersionsList> carVersionsList;
    public class carVersionsList{
        public String getCarVersionName() {
            return carVersionName;
        }

        public void setCarVersionName(String carVersionName) {
            this.carVersionName = carVersionName;
        }

        public List<CarSeries.carVersionsList.carVersions> getCarVersions() {
            return carVersions;
        }

        public void setCarVersions(List<CarSeries.carVersionsList.carVersions> carVersions) {
            this.carVersions = carVersions;
        }

        public String carVersionName;
        public List<carVersions> carVersions;
        public class carVersions{
            public String carVersionId;//:"A2"//车系id，用于查看车型时用
            public String carIcon;//:"http://dsadsad.png" //车系图
            public String carVersionName;//:"奔驰A4L" //汽车系名字
            public String carPriceZone;//:"29.98 - 43.00万"

            public String getCarVersionId() {
                return carVersionId;
            }

            public void setCarVersionId(String carVersionId) {
                this.carVersionId = carVersionId;
            }

            public String getCarIcon() {
                return carIcon;
            }

            public void setCarIcon(String carIcon) {
                this.carIcon = carIcon;
            }

            public String getCarVersionName() {
                return carVersionName;
            }

            public void setCarVersionName(String carVersionName) {
                this.carVersionName = carVersionName;
            }

            public String getCarPriceZone() {
                return carPriceZone;
            }

            public void setCarPriceZone(String carPriceZone) {
                this.carPriceZone = carPriceZone;
            }
        }
    }

}
