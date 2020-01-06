package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import yizhit.workerlib.entites.*;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


@DisallowConcurrentExecution
public class SelectQuartzArvhivesInfo {
    private static final Logger log = LogManager.getLogger(SelectQuartzProjectInfo.class);

    public void batchInsertArvhivesInfo(){
        // 数据库数据
        System.out.println("查询工程员工表工作正在进入处理...");
        SelectQuartzUnitrInfo selectQuartzUnitrInfo = new SelectQuartzUnitrInfo();
        JSONObject params = new JSONObject();
        JSONArray array = null;
        int pageIndex = 0;
        try {
            //校验验码
            JSONObject jsonObject = new JSONObject();
            ProjectInfo projectInfo = new ProjectInfo();
            //查询工程ID
            List<ProjectInfo> projectInfoList = projectInfo.where("1=1").select(" project_id ").query(ProjectInfo.class);
            for (ProjectInfo projectInfoitem:projectInfoList) {
                TimerProfile timerProfile = new TimerProfile();
                timerProfile.setKey("archives");
                timerProfile.setPid(projectInfoitem.getEafId());
                TimerProfile currentTimerProfile = timerProfile.where("[key]=#{key}").and("[pid]=#{pid}").first();
                if(currentTimerProfile!=null) {
                    pageIndex = currentTimerProfile.getValue();
                }else{
                    timerProfile.setValue(1);
                    Integer i = timerProfile.insert();
                    pageIndex = 1;
                }

                //拼接密文
                StringBuilder sb = new StringBuilder();
                jsonObject.put("prjid",projectInfoitem.getEafId());
                jsonObject.put("pageNum",pageIndex);
                String formatDate = Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                sb.append("appid=appid1").append("&data="+jsonObject.toJSONString()).append("&format=json").append("&method=com2user.info").append("&nonce=123456").append("&timestamp="+formatDate).append("&version=1.0").append("&appsecret=123456");
                String hex = sb.toString().toLowerCase();
                String s = SHA256.getSHA256StrJava(hex);
                System.out.println("cd:"+s);
                System.out.println(formatDate);
                //发送请求
                params.put("method","com2user.info");
                params.put("format","json");
                params.put("version","1.0");
                params.put("appid","appid1");
                params.put("timestamp",formatDate);
                params.put("nonce","123456");
                params.put("sign",s);
                params.put("data",jsonObject.toJSONString());
                String str = params.toJSONString();
                System.out.println("params:  " + str);
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
                String  result = RequestUtils.post(FinalUtil.url, str, header );
                JSONObject json = JSONObject.parseObject(result);

                List<ArchivesInfo> archivesInfoList = new ArrayList<ArchivesInfo>();
                // 数据获取正确
                if(json.containsKey("code") && json.get("code").equals("0")){
                    array = json.getJSONObject("data").getJSONArray("list");
                    String text = array.toJSONString();
                    archivesInfoList =  FastJsonUtils.toList(text, ArchivesInfo.class);
                    if (archivesInfoList.size() == 500){
                        pageIndex++;
                    }
                    for(ArchivesInfo info:archivesInfoList){
                        try {
                            //给所有人员表插入单位ID
                            AllUserInfoUpdate allUserInfoUpdate = new AllUserInfoUpdate();
                            allUserInfoUpdate.setCwrIdnum(info.getCwrIdnum());
                            allUserInfoUpdate.setUnitId(info.getCwrComid());
                            allUserInfoUpdate.where("[cwrIdnum]=#{cwrIdnum}").update("[unit_id]=#{unitId}");

//                            //给所有工种表导入工种信息
                            ProjectWorkType projectWorkType = new ProjectWorkType();
                            projectWorkType.setEafId(info.getUserid());
                            projectWorkType.setProjectId(info.getCwrPrjid());
                            projectWorkType.setWorkType(info.getCwrWorkName());
                            projectWorkType.setCreateBy("1");
                            projectWorkType.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            projectWorkType.setUserPath("0/1");
                            ProjectWorkType jsEafid = projectWorkType.where("[eafId] = #{eafId}").first();
                            if (jsEafid == null){
                                projectWorkType.insert();
                            }else{
                                projectWorkType.where("[eafId]=#{eafId}").and("[projectId]=#{projectId}").update("[workType]=#{workType}");
                            }

                            //给工种表导入工种信息
                            WorkType workType = new WorkType();
                            workType.setEafId(info.getUserid());
                            workType.setWorkType(info.getCwrWorkName());
                            workType.setCreateBy("1");
                            workType.setCreateOn(Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            WorkType jstype_id  = workType.where("[eafId] = #{eafId}").first();
                            if (jstype_id == null){
                                workType.insert();
                            }else{
                                workType.where("[eafId]=#{eafId}").update("[workType]=#{workType}");
                            }

                            if(info.getEafName() != null && !Pattern.compile("[0-9]*").matcher(info.getEafName()).matches()){
                                info.setLeave(2);
                                if("进行中".equals(info.getCwrUserStatus())) {
                                    info.setLeave(1);
                                }
                                selectQuartzUnitrInfo.batchInsertUnitrInfo(info.getCwrComid());
                                ArchivesInfo archivesInfo = new ArchivesInfo();
                                archivesInfo.setUserid(info.getUserid());
                                archivesInfo.setCwrPrjid(info.getCwrPrjid());
                                archivesInfo.setEafId(info.getEafId());
                                archivesInfo.setCwrUserStatus(info.getCwrUserStatus());
                                archivesInfo.setCwrComid(info.getCwrComid());
                                archivesInfo.setEafName(info.getEafName());
                                archivesInfo.setEafPhone(info.getEafPhone());
                                archivesInfo.setCwrIdnumType(info.getCwrIdnumType());
                                archivesInfo.setCwrIdnum(info.getCwrIdnum());
                                archivesInfo.setCwrWorkClass(info.getCwrWorkClass());
                                archivesInfo.setCwrWorkName(info.getCwrWorkName());
                                archivesInfo.setEafCreatetime(info.getEafCreatetime());
                                archivesInfo.setEafModifier(info.getEafModifier());
                                archivesInfo.setEafCreator(info.getEafCreator());
                                archivesInfo.setEafRLeftid(info.getEafRLeftid());
                                archivesInfo.setLeave(info.getLeave());
                                archivesInfo.setCwrWorkclassId(info.getCwrWorkclassId());
                                archivesInfo.setCwrWorktype(info.getCwrWorktype());
                                archivesInfo.setCwrUserIn(info.getCwrUserIn());
                                archivesInfo.setCwrUserOut(info.getCwrUserOut());
                                ArchivesInfo js = archivesInfo.where("[archives_id]=#{userid}").and("[project_id]=#{cwrPrjid}").first();
                                if (js == null ){
                                    Integer i = info.insert();
                                }else if ("结束".equals(info.getCwrUserStatus())){
                                    archivesInfo.setLeave(2);
                                    archivesInfo.where("[archives_id]=#{userid}").and("[project_id]=#{cwrPrjid}").update("[cwrUserStatus]=#{cwrUserStatus},[eafId]=#{eafId},[unit_id]=#{cwrComid}," +
                                            "[name]=#{eafName},[phone]=#{eafPhone},[cwrIdnumType]=#{cwrIdnumType}," +
                                            "[id_number]=#{cwrIdnum},[CwrWorkClass]=#{CwrWorkClass},[work_type]=#{CwrWorkName}," +
                                            "[createOn]=#{eafCreatetime},[modifyBy]=#{eafModifier},[modifyOn]=#{eafModifytime}," +
                                            "[createBy]=#{eafCreator},[eafRLeftid]=#{eafRLeftid},[cwrWorkclassId]=#{cwrWorkclassId}," +
                                            "[cwrWorktype]=#{cwrWorktype},[cwrUserIn]=#{cwrUserIn},[cwrUserOut]=#{cwrUserOut}," +
                                            "[leave]=#{leave}");
                                }else{
                                    //不更新状态
                                    archivesInfo.where("[archives_id]=#{userid}").and("[project_id]=#{cwrPrjid}").update("[cwrUserOut]=#{cwrUserOut},[eafId]=#{eafId},[unit_id]=#{cwrComid}," +
                                            "[name]=#{eafName},[phone]=#{eafPhone},[cwrIdnumType]=#{cwrIdnumType}," +
                                            "[id_number]=#{cwrIdnum},[CwrWorkClass]=#{CwrWorkClass},[work_type]=#{CwrWorkName}," +
                                            "[createOn]=#{eafCreatetime},[modifyBy]=#{eafModifier},[modifyOn]=#{eafModifytime}," +
                                            "[createBy]=#{eafCreator},[eafRLeftid]=#{eafRLeftid},[cwrWorkclassId]=#{cwrWorkclassId}," +
                                            "[cwrWorktype]=#{cwrWorktype},[cwrUserIn]=#{cwrUserIn}");
                                    }
                            }
                        }catch (Exception e){
                            log.error("插入项目下的人员信息出错： =============================================================>",e);
                            log.error(new Date());
                        }
                    }
                    timerProfile.setValue(pageIndex);
                    timerProfile.where("[key]=#{key}").and("[pid]=#{pid}").update("[value]=#{value}");
                    System.out.println("数据插入完成!");
                }else {
                    System.out.println("error:  " + json.toJSONString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }



    /**
     * 给工程人员表插入头像
     */
    public void updateArchivesPhoto(){
        AllUserInfo info = new AllUserInfo();
        try {
            List<AllUserInfo> infoList = info.select("cwrIdnum,cwrPhoto").query();
            for (AllUserInfo userInfo:infoList) {
                ArchivesInfoUpdate archivesInfo = new ArchivesInfoUpdate();
                archivesInfo.setCwrIdnum(userInfo.getCwrIdnum());
                archivesInfo.setPhoto(userInfo.getCwrPhoto());
                archivesInfo.where("id_number=#{cwrIdnum}").update("photo=#{photo}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
