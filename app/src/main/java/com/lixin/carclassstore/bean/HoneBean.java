package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/4/5
 * My mailbox is 1403241630@qq.com
 */

public class HoneBean {
    private String result;
    private String resultNote;


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



    @Override
    public String toString() {
        return "HomeNewsBean [result=" + result
                + ", resultNote=" + resultNote
                + "]";
    }
}


