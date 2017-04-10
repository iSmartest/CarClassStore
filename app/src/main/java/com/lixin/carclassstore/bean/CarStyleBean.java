package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/8
 * My mailbox is 1403241630@qq.com
 */

public class CarStyleBean {
    public String result;
    public String resultNote;

    public List<hotCarsList> hotCarsList;
    public List<carsSelectList> carsSelectList;
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

    public List<CarStyleBean.hotCarsList> getHotCarsList() {
        return hotCarsList;
    }

    public void setHotCarsList(List<CarStyleBean.hotCarsList> hotCarsList) {
        this.hotCarsList = hotCarsList;
    }

    public List<CarStyleBean.carsSelectList> getCarsSelectList() {
        return carsSelectList;
    }

    public void setCarsSelectList(List<CarStyleBean.carsSelectList> carsSelectList) {
        this.carsSelectList = carsSelectList;
    }

    public class hotCarsList{
        public String carbrandId;
        public String carleader;
        public String carname;

        public String getCarBrandId() {
            return carbrandId;
        }

        public void setCarBrandId(String carBrandId) {
            this.carbrandId = carBrandId;
        }

        public String getCarleader() {
            return carleader;
        }

        public void setCarleader(String carleader) {
            this.carleader = carleader;
        }

        public String getCarName() {
            return carname;
        }

        public void setCarName(String carName) {
            this.carname = carName;
        }
    }
    public class carsSelectList {
        public String carbrandId;
        public String carleader;
        public String carname;
        public String getCarBrandId() {
            return carbrandId;
        }

        public void setCarBrandId(String carBrandId) {
            this.carbrandId = carBrandId;
        }

        public String getCarleader() {
            return carleader;
        }

        public void setCarleader(String carleader) {
            this.carleader = carleader;
        }

        public String getCarName() {
            return carname;
        }

        public void setCarName(String carName) {
            this.carname = carName;
        }
    }
}
