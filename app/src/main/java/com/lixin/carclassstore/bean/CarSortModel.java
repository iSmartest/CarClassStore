package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/4/5
 * My mailbox is 1403241630@qq.com
 */

public class CarSortModel {
    private String sortLetters;  //显示数据拼音的首字母
    private String carBrandId;//汽车id;
    private String carName; //汽车名字；
    private String carleader; //汽车标示
    public CarSortModel(){

    }
    public CarSortModel(String sortLetters,String carBrandId,String carName,String carleader){
        this.sortLetters = sortLetters;
        this.carBrandId = carBrandId;
        this.carName = carName;
        this.carleader = carleader;
    }

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

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarleader() {
        return carleader;
    }

    public void setCarleader(String carleader) {
        this.carleader = carleader;
    }
}
