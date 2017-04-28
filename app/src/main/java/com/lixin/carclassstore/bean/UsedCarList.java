package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/26
 * My mailbox is 1403241630@qq.com
 */

public class UsedCarList {
    public String result;
    public String resultNote;
    public List<carModelList> carModelList;

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

    public List<UsedCarList.carModelList> getCarModelList() {
        return carModelList;
    }

    public void setCarModelList(List<UsedCarList.carModelList> carModelList) {
        this.carModelList = carModelList;
    }

    public class carModelList{
        public String carM;
        public String carid;
        public String carModel;
        public String carIcon;
        public String carRunKm;
        public String carBuyYear;
        public String carsafeuard;
        public String carNowPrice;

        public String getCarM() {
            return carM;
        }

        public void setCarM(String carM) {
            this.carM = carM;
        }

        public String getCarid() {
            return carid;
        }

        public void setCarid(String carid) {
            this.carid = carid;
        }

        public String getCarModel() {
            return carModel;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public String getCarIcon() {
            return carIcon;
        }

        public void setCarIcon(String carIcon) {
            this.carIcon = carIcon;
        }

        public String getCarRunKm() {
            return carRunKm;
        }

        public void setCarRunKm(String carRunKm) {
            this.carRunKm = carRunKm;
        }

        public String getCarBuyYear() {
            return carBuyYear;
        }

        public void setCarBuyYear(String carBuyYear) {
            this.carBuyYear = carBuyYear;
        }

        public String getCarsafeuard() {
            return carsafeuard;
        }

        public void setCarsafeuard(String carsafeuard) {
            this.carsafeuard = carsafeuard;
        }

        public String getCarNowPrice() {
            return carNowPrice;
        }

        public void setCarNowPrice(String carNowPrice) {
            this.carNowPrice = carNowPrice;
        }
    }
}
