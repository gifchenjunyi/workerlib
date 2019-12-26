package yizhit.workerlib.trigger;

import ccait.ccweb.annotation.*;
import ccait.ccweb.filter.RequestWrapper;
import entity.query.Datetime;
import org.springframework.stereotype.Component;
import yizhit.workerlib.entites.Group;
import yizhit.workerlib.entites.InvoLvedproject;
import yizhit.workerlib.entites.Privilege;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Component
@Trigger(tablename = "project") //触发器注解
public final class PrjectTrigger {
    /***
     * 新增数据事件
     * @param list （提交的数据）
     * @param request （当前请求）
     * @throws Exception
     */
    @OnInsert
    public void onInsert(List<Map<String, Object>> list, HttpServletRequest request) throws Exception {
        String groupId = UUID.randomUUID().toString().replace("-", "");
        for(Map item : list) {
            //插入一条数据到历史记录表
            Group group = new Group();
            group.setGroupId(groupId);
            group.setGroupName((String)item.get("project_name"));
            group.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            group.insert();

            //获取Privilege表的id
            String privilegeId = UUID.randomUUID().toString().replace("-", "");
            Privilege privilege = new Privilege();
            privilege.setPrivilegeId(privilegeId);
            privilege.setGroupId(groupId);
            privilege.setRoleId("9d83cad925124244b1b5ec7cf0656015");
            privilege.setCanAdd(1);
            privilege.setCanDelete(1);
            privilege.setCanUpdate(1);
            privilege.setCanView(1);
            privilege.setCanDownload(1);
            privilege.setCanPreviewDoc(1);
            privilege.setCanUpload(1);
            privilege.setCanExport(1);
            privilege.setCanImport(1);
            privilege.setCanDecrypt(1);
            privilege.setCanList(1);
            privilege.setCanQuery(1);
            privilege.setScope(4);
            privilege.setUserPath("0/1");
            privilege.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            privilege.insert();
        }
        RequestWrapper wrapper = (RequestWrapper) request;
        wrapper.setPostParameter(list);
    }


}

