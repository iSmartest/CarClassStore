package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/25
 * My mailbox is 1403241630@qq.com
 */

public class CarModel {
    public String result;
    public String resultNote;
    public List<carModelList>carModelList;

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

    public List<CarModel.carModelList> getCarModelList() {
        return carModelList;
    }

    public void setCarModelList(List<CarModel.carModelList> carModelList) {
        this.carModelList = carModelList;
    }

    public class carModelList {
        public String carId;
        public String carModel;
        public String carDisplacement;

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

        public String getCarDisplacement() {
            return carDisplacement;
        }

        public void setCarDisplacement(String carDisplacement) {
            this.carDisplacement = carDisplacement;
        }
    }
}
