package yizhit.workerlib.entites;

import entity.query.Queryable;
import entity.query.annotation.Fieldname;
import entity.query.annotation.Tablename;

import java.io.Serializable;

@Tablename("project")
public class ProjectInfo  extends Queryable<ProjectInfo> implements Serializable {

    @Fieldname("project_id")
    private String eafId;           //id

    @Fieldname("modifyOn")
    private String eafModifytime;   //更新时间

    @Fieldname("createOn")
    private String eafCreatetime;   //创建时间

    @Fieldname("project_address")
    private String cwrPrjAddr;

    @Fieldname("project_name")
    private String cwrPrjName;      //项目名称

    @Fieldname("project_brief")
    private String cwrPrjJian;      //项目描述

    @Fieldname("status")
    private String cwrPrjStatus;    //状态

    private String cwrPrjType;      //类型

    private String cwrPrjCode;      //工程编码

/*
    private String cwrOrgName;
    private String cwrOrgJian;
    private String cwrPrjDir;
    private String cwrCostEng;
    private String cwrPreEng;
    private String cwrProEng;
    private String cwrFgpfCode;
    private String cwrRecYear;
    private String cwrMajCat;
    private String cwrTotalArea;
    private String cwrKspfLx;
    private String eafModifier;
    private String eafCreator;
    private String cwrPrjMan;
    private String cwrGspfDy;
    private String cwrPfgsTz;
    private String cwrAppCode;
    private String cwrGcghxkCode;
    private String cwrYdghxkCode;
    private String cwrXzyjsCode;
    private String cwrConCode;
    private String cwrStartDate;
    private String cwrPrjStage;
    private String cwrUseCom;
    private String cwrCapFrom;
    private String cwrSupUnit;
    private String cwrJdbh;
    private String cwrPrjArea;
    private String cwrPrjPhoto;
    private String cwrAddrJd;
    private String cwrAddrWd;
    private String cwrIsvproj;
    private String cwrSgUnit;
    private String cwrPrjGcbh;
    private String cwrPrjIslinked;
    private String cwrPrjIssalary;
    private String cwrJsUnit;
    private String cwrLinkedDate;
    private String cwrSalaryDate;
    private String cwrStorageGold;
    private String cwrControlUnit;
    private String cwrDesignUnit;
    private String cwrQualityUnit;
    private String cwrSafetyUnit;
    private String cwrPaAddr;
    private String cwrPrjIsbegin;
    private String cwrEnginName;
    private String cwrEnginAddr;
    private String cwrPrjIscity;
    private String cwrPrjIstown;
    private String cwrRedArea;
    private String cwrPrjScan;
    private String cwrPrjDes;
    private String cwrPrjView;

*/

    public String getEafId() {
        return eafId;
    }

    public void setEafId(String eafId) {
        this.eafId = eafId;
    }


    public String getEafModifytime() {
        return eafModifytime;
    }

    public void setEafModifytime(String eafModifytime) {
        this.eafModifytime = eafModifytime;
    }

    public String getEafCreatetime() {
        return eafCreatetime;
    }

    public void setEafCreatetime(String eafCreatetime) {
        this.eafCreatetime = eafCreatetime;
    }

    public String getCwrPrjName() {
        return cwrPrjName;
    }

    public void setCwrPrjName(String cwrPrjName) {
        this.cwrPrjName = cwrPrjName;
    }

    public String getCwrPrjJian() {
        return cwrPrjJian;
    }

    public void setCwrPrjJian(String cwrPrjJian) {
        this.cwrPrjJian = cwrPrjJian;
    }

    public String getCwrPrjAddr() {
        return cwrPrjAddr;
    }

    public void setCwrPrjAddr(String cwrPrjAddr) {
        this.cwrPrjAddr = cwrPrjAddr;
    }

    public String getCwrPrjStatus() {
        return cwrPrjStatus;
    }

    public void setCwrPrjStatus(String cwrPrjStatus) {
        this.cwrPrjStatus = cwrPrjStatus;
    }

    public String getCwrPrjType() {
        return cwrPrjType;
    }

    public void setCwrPrjType(String cwrPrjType) {
        this.cwrPrjType = cwrPrjType;
    }

    public String getCwrPrjCode() {
        return cwrPrjCode;
    }

    public void setCwrPrjCode(String cwrPrjCode) {
        this.cwrPrjCode = cwrPrjCode;
    }
}
