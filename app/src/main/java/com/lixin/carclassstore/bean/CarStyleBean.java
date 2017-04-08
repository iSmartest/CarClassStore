package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/8
 * My mailbox is 1403241630@qq.com
 */

public class CarStyleBean {
    private String result;
    private String resultNote;

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
        private String carBrandId;
        private String carleader;
        private String carName;

        public String getCarBrandId() {
            return carBrandId;
        }

        public void setCarBrandId(String carBrandId) {
            this.carBrandId = carBrandId;
        }

        public String getCarleader() {
            return carleader;
        }

        public void setCarleader(String carleader) {
            this.carleader = carleader;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }
    }
    public class carsSelectList {
        private String carBrandId;
        private String carleader;
        private String carName;
        private String sortLetters;
        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }
        public String getCarBrandId() {
            return carBrandId;
        }

        public void setCarBrandId(String carBrandId) {
            this.carBrandId = carBrandId;
        }

        public String getCarleader() {
            return carleader;
        }

        public void setCarleader(String carleader) {
            this.carleader = carleader;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }
    }
}
