package com.lixin.carclassstore.bean;


import java.util.List;

/**
 * Created by 小火
 * Create time on  2017/4/14
 * My mailbox is 1403241630@qq.com
 */

public class RoadRescueBean {
    public String result;//"0" //0成功1失败
    public String resultNote;//"失败原因"
    public List<rescueList> rescueList;
    public List<accidentTypes> accidentTypes;
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

    public List<RoadRescueBean.rescueList> getRescueList() {
        return rescueList;
    }

    public void setRescueList(List<RoadRescueBean.rescueList> rescueList) {
        this.rescueList = rescueList;
    }

    public List<RoadRescueBean.accidentTypes> getAccidentTypes() {
        return accidentTypes;
    }

    public void setAccidentTypes(List<RoadRescueBean.accidentTypes> accidentTypes) {
        this.accidentTypes = accidentTypes;
    }

    public class rescueList{
        public String accidentid;//""
        public String accidentType;//""//事故类型
        public String accidentHandleType;//""//事故处理状态0为已处理，1为未处理
        public String accidentTime;//""//事故提交时间

        public String getAccidentid() {
            return accidentid;
        }

        public void setAccidentid(String accidentid) {
            this.accidentid = accidentid;
        }

        public String getAccidentType() {
            return accidentType;
        }

        public void setAccidentType(String accidentType) {
            this.accidentType = accidentType;
        }

        public String getAccidentHandleType() {
            return accidentHandleType;
        }

        public void setAccidentHandleType(String accidentHandleType) {
            this.accidentHandleType = accidentHandleType;
        }

        public String getAccidentTime() {
            return accidentTime;
        }

        public void setAccidentTime(String accidentTime) {
            this.accidentTime = accidentTime;
        }
    }
    public class accidentTypes{
        public String accidentTypes;//事故类型

        public String getAccidentTypes() {
            return accidentTypes;
        }

        public void setAccidentTypes(String accidentTypes) {
            this.accidentTypes = accidentTypes;
        }
    }
}
