package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/13
 * My mailbox is 1403241630@qq.com
 */

public class CustomerServiceBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public List<service> service;

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

    public List<CustomerServiceBean.service> getService() {
        return service;
    }

    public void setService(List<CustomerServiceBean.service> service) {
        service = service;
    }

    public class service{
        public String serviceName;//"客服小赵"//
        public String serviceQQ;//"65663251"
        public String serviceWX;//"31232113"
        public String serviceTelephone;//"1862355333"

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceQQ() {
            return serviceQQ;
        }

        public void setServiceQQ(String serviceQQ) {
            this.serviceQQ = serviceQQ;
        }

        public String getServiceWX() {
            return serviceWX;
        }

        public void setServiceWX(String serviceWX) {
            this.serviceWX = serviceWX;
        }

        public String getServiceTelephone() {
            return serviceTelephone;
        }

        public void setServiceTelephone(String serviceTelephone) {
            this.serviceTelephone = serviceTelephone;
        }
    }
}
