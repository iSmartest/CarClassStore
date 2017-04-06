package com.lixin.carclassstore.homeBean;

/**
 * Created by Administrator on 2017/4/5.
 */

public class ShopMenuData {
    private String meunType;
    private String advertisementIcon;
    private ShopMenuMData meun;

    public String getMeunType() {
        return meunType;
    }

    public void setMeunType(String meunType) {
        this.meunType = meunType;
    }

    public String getAdvertisementIcon() {
        return advertisementIcon;
    }

    public void setAdvertisementIcon(String advertisementIcon) {
        this.advertisementIcon = advertisementIcon;
    }

    public ShopMenuMData getMeun() {
        return meun;
    }

    public void setMeun(ShopMenuMData meun) {
        this.meun = meun;
    }
}
