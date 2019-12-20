package yizhit.workerlib.entites;

import entity.query.Queryable;

/**
 * 历史记录表
 */
public class InvoLvedproject extends Queryable<InvoLvedproject> {

   private int id;                  //自增列id
   private String archives_id ;     //工程下的人员id
   private String project_id ;      //工程id
   private String unit_id;          //单位id
   private String start_time;       //开始时间
   private String end_time ;        //结束时间
   private String userPath;         //用户地址
   private String modifyBy ;        //更新人
   private String modifyTime ;      //更新时间
   private String createOn ;          //创建时间
   private long createBy ;        //创建人

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchivesId() {
        return archives_id;
    }

    public void setArchivesId(String archives_id) {
        this.archives_id = archives_id;
    }

    public String getProjectId() {
        return project_id;
    }

    public void setProjectid(String project_id) {
        this.project_id = project_id;
    }

    public String getUnitId() {
        return unit_id;
    }

    public void setUnitId(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getDatetime() {
        return modifyTime;
    }

    public void setDatetime(String datetime) {
        this.modifyTime = datetime;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }
}
