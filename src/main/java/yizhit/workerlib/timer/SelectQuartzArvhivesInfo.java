package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import yizhit.workerlib.entites.*;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SelectQuartzArvhivesInfo {
    public void batchInsertArvhivesInfo(){
        // 数据库数据
        System.out.println("查询工程员工表工作正在进入处理...");
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
                        try{
                            if(info.getEafName() != null && !Pattern.compile("[0-9]*").matcher(info.getEafName()).matches()){
                                info.setLeave(2);
                                if("进行中".equals(info.getCwrUserStatus())) {
                                    info.setLeave(1);
                                }
                                ArchivesInfo archivesInfo = new ArchivesInfo();
                                archivesInfo.setUserid(info.getUserid());
                                archivesInfo.setCwrPrjid(projectInfoitem.getEafId());
                                ArchivesInfo js = archivesInfo.where("archives_id=#{userid}").first();
                                if (js == null ){
                                    Integer i = info.insert();
                                }else if ("结束".equals(info.getCwrUserStatus())){
                                    info.setLeave(2);
                                    archivesInfo.where("archives_id=#{userid}").update("cwrUserStatus=#{cwrUserStatus}");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("error:============"+e);
                        }
                    }
                    System.out.println("数据插入完成!");
                    timerProfile.setValue(pageIndex);
                    timerProfile.where("[key]=#{key}").and("[pid]=#{pid}").update("[value]=#{value}");
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
