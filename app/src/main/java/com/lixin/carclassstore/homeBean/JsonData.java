package com.lixin.carclassstore.homeBean;

/**
 * Created by Administrator on 2017/4/5.
 */

public class JsonData {
    private String result;
    private String resultNote;
    private  ServeData serveData;
    private  ShopMenuData shopMenuData;
    private  StoreMenuData storeMenuData;


    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public ServeData getServeData() {
        return serveData;
    }

    public void setServeData(ServeData serveData) {
        this.serveData = serveData;
    }

    public ShopMenuData getShopMenuData() {
        return shopMenuData;
    }

    public void setShopMenuData(ShopMenuData shopMenuData) {
        this.shopMenuData = shopMenuData;
    }

    public StoreMenuData getStoreMenuData() {
        return storeMenuData;
    }

    public void setStoreMenuData(StoreMenuData storeMenuData) {
        this.storeMenuData = storeMenuData;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
