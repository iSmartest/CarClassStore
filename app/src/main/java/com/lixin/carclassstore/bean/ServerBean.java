package com.lixin.carclassstore.bean;

import com.lixin.carclassstore.homeBean.RotateAdvertisementBean;
import com.lixin.carclassstore.homeBean.ServeBottomBean;
import com.lixin.carclassstore.homeBean.ServeTopBean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/5
 * My mailbox is 1403241630@qq.com
 */

public class ServerBean {

    private List<ServeTopBean> serveTop;
    private List<ServeBottomBean> serveBottom;
    private List<RotateAdvertisementBean> rotateAdvertisement;
    private CheckAdvertisementBean checkAdvertisement;



    public List<ServeTopBean> getServeTop() {
        return serveTop;
    }

    public void setServeTop(List<ServeTopBean> serveTop) {
        this.serveTop = serveTop;
    }

    public List<ServeBottomBean> getServeBottom() {
        return serveBottom;
    }

    public void setServeBottom(List<ServeBottomBean> serveBottom) {
        this.serveBottom = serveBottom;
    }

    public List<RotateAdvertisementBean> getRotateAdvertisement() {
        return rotateAdvertisement;
    }

    public void setRotateAdvertisement(List<RotateAdvertisementBean> rotateAdvertisement) {
        this.rotateAdvertisement = rotateAdvertisement;
    }

    public CheckAdvertisementBean getCheckAdvertisement() {
        return checkAdvertisement;
    }

    public void setCheckAdvertisement(CheckAdvertisementBean checkAdvertisement) {
        this.checkAdvertisement = checkAdvertisement;
    }

    @Override
    public String toString() {
        return "ServerBean [serveTop=" + serveTop + ", serveBottom="
                + serveBottom + ", rotateAdvertisement=" + rotateAdvertisement + ", checkAdvertisement=" + checkAdvertisement
                + "]";
    }
}
