package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Value;
import yizhit.workerlib.entites.*;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.util.*;

@DisallowConcurrentExecution
public class SelectQuartzProjectInfo {

    private static final Logger log = LogManager.getLogger(SelectQuartzProjectInfo.class);

    @Value("${enableTasks:false}")
    private Boolean enableTasks;

    public  void batchInsertProjectInfo(){

        if(!enableTasks) {
            return;
        }

        // 数据库数据
        log.info("查询所有项目工作正在进入处理...");
        JSONObject params = new JSONObject();
        JSONArray array = null;
        int pageIndex = 0;
        try {
            StringBuilder sb = new StringBuilder();
            JSONObject jsonObject = new JSONObject();
//          jsonObject.put("idnum","445122199309195215");
            TimerProfile timerProfile = new TimerProfile();
            timerProfile.setKey("project");
            TimerProfile currentTimerProfile = timerProfile.where("[key]=#{key}").first();
            if(currentTimerProfile!=null) {
                pageIndex = currentTimerProfile.getValue();
            }else{
                timerProfile.setValue(1);
                Integer i = timerProfile.insert();
                pageIndex = 1;
            }
            //拼接校验码
            jsonObject.put("pageNum", pageIndex);
            String formatDate = Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            sb.append("appid=appid1").append("&data="+jsonObject.toJSONString()).append("&format=json").append("&method=project.info").append("&nonce=123456").append("&timestamp=").append(formatDate).append("&version=1.0").append("&appsecret=123456");
            String hex = sb.toString().toLowerCase();
            String s = SHA256.getSHA256StrJava(hex);
            System.out.println("cd:"+s);

            params.put("method","project.info");
            params.put("format","json");
            params.put("version","1.0");
            params.put("appid","appid1");
            params.put("timestamp",formatDate);
            params.put("nonce","123456");
            params.put("sign",s);
            params.put("data",jsonObject.toJSONString());
            String str = params.toJSONString();
            log.info("params:  " + str);
            log.info(formatDate);
            HashMap<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json");
            String result = RequestUtils.post(FinalUtil.url, str, header );
            JSONObject json = JSONObject.parseObject(result);

            List<ProjectInfo> list = new ArrayList<ProjectInfo>();
            // 数据获取正确
            if(json.containsKey("code") && json.get("code").equals("0")) {
                array = json.getJSONObject("data").getJSONArray("list");
                list = (List<ProjectInfo>) FastJsonUtils.toList(array.toJSONString(), ProjectInfo.class);
                if (list.size() == 500) {
                    pageIndex++;
                }
            }

            for(ProjectInfo item : list){
                String groupId = UUID.randomUUID().toString().replace("-", "");
                try {
                    //获取Group表的id
                    Group group = new Group();
                    group.setGroupId(groupId);
                    group.setUserPath("0/1");
                    group.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    group.setGroupName(item.getCwrPrjName());
                    Group groupjs = group.where("[groupName]=#{groupName}").first();
                    if (groupjs == null){
                        group.insert();
                    }else{
                        group.where("[groupName]=#{groupName}").update("[groupName]=#{groupName}");
                    }

                    //查找管理员id
                    RoleModel roleModel= new RoleModel();
                    roleModel.setRoleName("管理员");
                    RoleModel roleId = roleModel.where("[roleName]=#{roleName}").first();

                    //获取Privilege表的id
                    String privilegeId = UUID.randomUUID().toString().replace("-", "");
                    Privilege privilege = new Privilege();
                    privilege.setPrivilegeId(privilegeId);
                    privilege.setGroupId(groupId);
                    privilege.setRoleId(roleId.getRoleId());
                    privilege.setCanAdd(1);
                    privilege.setCanDelete(1);
                    privilege.setCanUpdate(1);
                    privilege.setCanView(1);
                    privilege.setCanDownload(1);
                    privilege.setCanPreview(1);
                    privilege.setCanUpload(1);
                    privilege.setCanExport(1);
                    privilege.setCanImport(1);
                    privilege.setCanDecrypt(1);
                    privilege.setCanList(1);
                    privilege.setCanQuery(1);
                    privilege.setScope(4);
                    privilege.setUserPath("0/1");
                    privilege.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    Privilege privilegejs = privilege.where("[groupId]=#{groupId}").and("[roleId]=#{roleId}").first();
                    if (privilegejs == null){
                        privilege.insert();
                    }

                }catch(Exception e2){
                    log.error(FastJsonUtils.convertObjectToJSON(item));
                    log.error(e2);
                }
            }

            for(ProjectInfo item : list) {
                ProjectInfo js = null;
                ProjectInfo projectInfo = null;
                try {
                    projectInfo = new ProjectInfo();
                    projectInfo.setEafId(item.getEafId());
                    //查找是否有相同的公司
                    js = projectInfo.where("[project_id]=#{project_id}").first();
                    if (js == null) {
                        Integer i = item.insert();
                    } else {
                        projectInfo.setEafModifytime(item.getEafModifytime());
                        projectInfo.setEafCreatetime(item.getEafCreatetime());
                        projectInfo.setCwrPrjAddr(item.getCwrPrjAddr());
                        projectInfo.setCwrPrjName(item.getCwrPrjName());
                        projectInfo.setCwrPrjJian(item.getCwrPrjJian());
                        projectInfo.setCwrPrjStatus(item.getCwrPrjStatus());
                        projectInfo.setCwrPrjType(item.getCwrPrjType());
                        projectInfo.setCwrPrjCode(item.getCwrPrjCode());
                        projectInfo.setCwrEndDate(item.getCwrEndDate());
                        projectInfo.setCwrStartDate(item.getCwrStartDate());
                        projectInfo.setCwrJsUnit(item.getCwrJsUnit());
                        projectInfo.setCwrSgUnit(item.getCwrSgUnit());
                        projectInfo.setCwrControlUnit(item.getCwrControlUnit());
                        projectInfo.where("[project_id]=#{eafId}").update("[modifyOn]=#{eafModifytime},[createOn]=#{eafCreatetime},[project_address]=#{cwrPrjAddr}," +
                                "[project_name]=#{cwrPrjName},[project_brief]=#{cwrPrjJian},[status]=#{cwrPrjStatus}," +
                                "[cwrPrjType]=#{cwrPrjType},[cwrPrjCode]=#{cwrPrjCode},[end_time]=#{cwrEndDate}," +
                                "[start_time]=#{cwrStartDate},[construction]=#{cwrJsUnit},[organization]=#{cwrSgUnit}," +
                                "[supervising]=#{cwrControlUnit}");
                    }
                }
                catch (Exception e) {
                    if (js == null) {
                        log.error("项目数据插入失败: =============================================================>");
                        log.error(FastJsonUtils.convertObjectToJSON(item));
                    }
                    else {
                        log.error("项目数据更新失败: ------------------------------------------------------------>");
                        log.error(FastJsonUtils.convertObjectToJSON(projectInfo));
                    }
                    log.error("--------------------------------------------------------------------------------");
                    log.error(e);
                }
                Thread.sleep(1000);
            }
            System.out.println("数据插入完成!");
            timerProfile.setValue(pageIndex);
            timerProfile.where("[key]=#{key}").update("[value]=#{value}");
            Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
