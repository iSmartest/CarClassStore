package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/26
 * My mailbox is 1403241630@qq.com
 */

public class NewCarDetails {
    public String iphoneNum;
    public String result;
    public String resultNote;
    public List<String> rotateCarPics;
    public List<salesCars>salesCars;

    public String getIphoneNum() {
        return iphoneNum;
    }

    public void setIphoneNum(String iphoneNum) {
        this.iphoneNum = iphoneNum;
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

    public List<NewCarDetails.salesCars> getSalesCars() {
        return salesCars;
    }

    public void setSalesCars(List<NewCarDetails.salesCars> salesCars) {
        this.salesCars = salesCars;
    }

    public List<String> getRotateCarPics() {
        return rotateCarPics;
    }

    public void setRotateCarPics(List<String> rotateCarPics) {
        this.rotateCarPics = rotateCarPics;
    }

    public class salesCars{
        public String carFactoryPrice;
        public String carId;
        public String carModel;
        public String carNewPrice;

        public String getCarFactoryPrice() {
            return carFactoryPrice;
        }

        public void setCarFactoryPrice(String carFactoryPrice) {
            this.carFactoryPrice = carFactoryPrice;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getCarModel() {
            return carModel;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public String getCarNewPrice() {
            return carNewPrice;
        }

        public void setCarNewPrice(String carNewPrice) {
            this.carNewPrice = carNewPrice;
        }
    }
//    {
//        "praises": [],
//    }
}
