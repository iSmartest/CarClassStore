package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/4/12
 * My mailbox is 1403241630@qq.com
 */

public class ReplyBean {
    public String result;//"0" 0成功1失败
    public String resultNote;//"失败原因"

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
}
