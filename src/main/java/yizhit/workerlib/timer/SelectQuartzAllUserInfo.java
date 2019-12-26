package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import org.quartz.DisallowConcurrentExecution;
import yizhit.workerlib.entites.*;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@DisallowConcurrentExecution
public class SelectQuartzAllUserInfo {

    public void batchInsertArchivesInfo(){
        // 数据库数据
        System.out.println("查询所有人员工作正在进入处理...");
        JSONObject params = new JSONObject();
        JSONArray array = null;
        int pageIndex = 0;
        try {
            //校验验码
            JSONObject jsonObject = new JSONObject();
            //查询工程ID
            ProjectInfo projectInfo = new ProjectInfo();
            List<ProjectInfo> projectInfoList = projectInfo.where("1=1").select(" project_id ").query(ProjectInfo.class);
            for (ProjectInfo projectInfoitem:projectInfoList) {
                TimerProfile timerProfile = new TimerProfile();
                timerProfile.setKey("alluser");
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
                jsonObject.put("pageNum", pageIndex);
                String formatDate = Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                sb.append("appid=appid1").append("&data="+jsonObject.toJSONString()).append("&format=json").append("&method=user.info").append("&nonce=123456").append("&timestamp="+formatDate).append("&version=1.0").append("&appsecret=123456");
                String hex = sb.toString().toLowerCase();
                System.out.println(hex);
                String s = SHA256.getSHA256StrJava(hex);
                System.out.println("cd:"+s);
                System.out.println(formatDate);
                System.out.println(projectInfoitem.getEafId());
                //发送请求
                params.put("method","user.info");
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
                String result = RequestUtils.post(FinalUtil.url, str, header );
                JSONObject json = JSONObject.parseObject(result);

                List<AllUserInfo> allUserInfoList = new ArrayList<AllUserInfo>() ;
                // 数据获取正确
                if(json.containsKey("code") && json.get("code").equals("0")){
                    array = json.getJSONObject("data").getJSONArray("list");
                    String text = array.toJSONString();
                    allUserInfoList =  FastJsonUtils.toList(text, AllUserInfo.class);
                    if (allUserInfoList.size() == 500){
                        pageIndex++;
                    }
                    for(AllUserInfo info:allUserInfoList){
                        AllUserInfo allUserInfo = new AllUserInfo();
                        allUserInfo.setEafId(info.getEafId());
                        AllUserInfo js = allUserInfo.where("[eafId]=#{eafId}").first();
                        if (js == null){
                            Integer i = info.insert();
                        }else {
                            allUserInfo.setEafName(info.getEafName());
                            allUserInfo.setEafPhone(info.getEafPhone());
                            allUserInfo.setCwrIdnumType(info.getCwrIdnumType());
                            allUserInfo.setCwrIdnum(info.getCwrIdnum());
                            allUserInfo.setCwrIdphotoScan(info.getCwrIdphotoScan());
                            allUserInfo.setCwrPhoto(info.getCwrPhoto());
                            allUserInfo.setEafCreatetime(info.getEafCreatetime());
                            allUserInfo.setEafModifytime(info.getEafModifytime());
                            allUserInfo.setCwrIdaddr(info.getCwrIdaddr());
                            allUserInfo.setEafCreator(info.getEafCreator());
                            allUserInfo.setEafModifier(info.getEafModifier());
                            allUserInfo.setCwrStatus(info.getCwrStatus());
                            allUserInfo.setEafStatus(info.getEafStatus());
                            allUserInfo.where("[eafId]=#{eafId}").update("[eafName]=#{eafName},[eafPhone]=#{eafPhone},[cwrIdnumType]=#{cwrIdnumType}," +
                                                                                         "[cwrIdnum]=#{cwrIdnum},[id_card_front]=#{cwrIdphotoScan},[cwrPhoto]=#{cwrPhoto}," +
                                                                                         "[eafCreatetime]=#{eafCreatetime},[eafModifytime]=#{eafModifytime},[cwrIdaddr]=#{cwrIdaddr}," +
                                                                                         "[eafCreator]=#{eafCreator},[eafModifier]=#{eafModifier},[cwrStatus]=#{cwrStatus}," +
                                                                                         "[eafStatus]=#{eafStatus}");
                        }
                    }
                    System.out.println("数据插入完成!");
                    timerProfile.setValue(pageIndex);
                    timerProfile.where("[key]=#{key}").and("[pid]=#{pid}").update("[value]=#{value}");
                }
                else {
                    System.out.println("error:  " + result);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
