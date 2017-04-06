package com.lixin.carclassstore.homeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class ServeData {
    private List<ServeTopData>serveTop;
    private List<ServeBottomData>serveBottom;
    private List<RotateAdvertisementData>rotateAdvertisement;
    private List<CheckAdvertisementData>checkAdvertisement;

    public List<ServeTopData> getServeTop() {
        return serveTop;
    }

    public void setServeTop(List<ServeTopData> serveTop) {
        this.serveTop = serveTop;
    }

    public List<ServeBottomData> getServeBottom() {
        return serveBottom;
    }

    public void setServeBottom(List<ServeBottomData> serveBottom) {
        this.serveBottom = serveBottom;
    }

    public List<RotateAdvertisementData> getRotateAdvertisement() {
        return rotateAdvertisement;
    }

    public void setRotateAdvertisement(List<RotateAdvertisementData> rotateAdvertisement) {
        this.rotateAdvertisement = rotateAdvertisement;
    }

    public List<CheckAdvertisementData> getCheckAdvertisement() {
        return checkAdvertisement;
    }

    public void setCheckAdvertisement(List<CheckAdvertisementData> checkAdvertisement) {
        this.checkAdvertisement = checkAdvertisement;
    }
}
