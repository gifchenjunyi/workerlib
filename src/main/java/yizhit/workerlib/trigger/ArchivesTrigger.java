package yizhit.workerlib.trigger;

import ccait.ccweb.annotation.*;
import ccait.ccweb.filter.RequestWrapper;
import entity.query.Datetime;
import org.springframework.stereotype.Component;
import yizhit.workerlib.entites.InvoLvedproject;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Trigger(tablename = "archives") //触发器注解
public final class ArchivesTrigger {
    /***
     * 新增数据事件
     * @param list （提交的数据）
     * @param request （当前请求）
     * @throws Exception
     */
    @OnInsert
    public void onInsert(List<Map<String, Object>> list, HttpServletRequest request) throws Exception {
        for(Map item : list) {
            item.put("leave",1);
            item.put("cwrUserIn",  Datetime.now());
            //插入一条数据到历史记录表
            InvoLvedproject lvedproject = new InvoLvedproject();
            lvedproject.setArchivesId((String) item.get("archives_id"));
            lvedproject.setProjectid((String) item.get("project_id"));
            lvedproject.setUnitId((String)item.get("unit_id"));
            lvedproject.setUserPath((String)item.get("userPath"));
            lvedproject.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            lvedproject.setCreateBy((long)item.get("createBy"));
            lvedproject.insert();

        }
        RequestWrapper wrapper = (RequestWrapper) request;
        wrapper.setPostParameter(list);
    }


    /***
     * 更新数据事件
     * @param list （提交的数据）
     * @param request （当前请求）
     * @throws Exception
     */
    @OnUpdate
    public void onUpdate(List<Map<String, Object>> list, HttpServletRequest request) throws Exception {
        for(Map item : list) {
            item.put("leave",1);
            item.put("cwrUserOut",  Datetime.now());
            //插入一条数据到历史记录表
            InvoLvedproject lvedproject = new InvoLvedproject();
            lvedproject.setArchivesId((String) item.get("archives_id"));
            lvedproject.setProjectid((String) item.get("project_id"));
            lvedproject.setUnitId((String)item.get("unit_id"));
            lvedproject.setUserPath((String)item.get("userPath"));
            lvedproject.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            lvedproject.setCreateBy((long)item.get("createBy"));
            lvedproject.insert();

        }
        RequestWrapper wrapper = (RequestWrapper) request;
        wrapper.setPostParameter(list);
    }
}

