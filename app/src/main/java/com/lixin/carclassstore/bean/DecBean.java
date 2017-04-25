package com.lixin.carclassstore.bean;

import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/20
 * My mailbox is 1403241630@qq.com
 */

public class DecBean {
    public String result;//"0" //0成功1失败
    public String resultNote;
    public String commodityWebLink;
    public List<parameterTypes> parameterTypes;

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

    public String getCommodityWebLink() {
        return commodityWebLink;
    }

    public void setCommodityWebLink(String commodityWebLink) {
        this.commodityWebLink = commodityWebLink;
    }

    public List<DecBean.parameterTypes> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<DecBean.parameterTypes> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public class parameterTypes {
        public String parameterTypes;//"重量" //参数的类型
        public String parameters;

        public String getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(String parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }
    }
}
