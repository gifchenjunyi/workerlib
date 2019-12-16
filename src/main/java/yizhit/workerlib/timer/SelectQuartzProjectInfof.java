package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import yizhit.workerlib.entites.ProjectInfo;
import yizhit.workerlib.entites.TimerProfile;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SelectQuartzProjectInfof {


    public  void batchInsertProjectInfo(){
        // 数据库数据
        System.out.println("查询所有工程工作正在进入处理...");
        JSONObject params = new JSONObject();
        JSONArray array = null;
        int pageIndex = 0;
        try {
            StringBuilder sb = new StringBuilder();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idnum","445122199309195215");
            TimerProfile timerProfile = new TimerProfile();
            timerProfile.setKey("project");
            timerProfile = timerProfile.where("[key]=#{key}").first();
            if(timerProfile!=null) {
                pageIndex = timerProfile.getValue();
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
            System.out.println("params:  " + str);
            HashMap<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json");
            String result = RequestUtils.post(FinalUtil.url, str, header );
            JSONObject json = JSONObject.parseObject(result);

            List<ProjectInfo> list = new ArrayList<ProjectInfo>();
            // 数据获取正确
            if(json.containsKey("code") && json.get("code").equals("0")){
                array = json.getJSONObject("data").getJSONArray("list");
                list = (List<ProjectInfo>) FastJsonUtils.toList(array.toJSONString(), ProjectInfo.class);
                if (list.size() == 500){
                    pageIndex++;
                }
                for(ProjectInfo item : list) {
                    try {
                        ProjectInfo projectInfo = new ProjectInfo();
                        projectInfo.setEafId(item.getEafId());
                        projectInfo = projectInfo.where("project_id=#{eafId}").first();
                        if (projectInfo == null){
                            Integer i = item.insert();
                        }
                    } catch (Exception e) {
                        System.out.println("error:===================  " + e);
                    }
                }
                System.out.println("数据插入完成!");
                timerProfile.setValue(pageIndex);
                timerProfile.where("[key]=#{key}").update("[value]=#{value}");
            }
            else {
                System.out.println("error:  " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
