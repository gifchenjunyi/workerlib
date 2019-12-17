package yizhit.workerlib.timer;

import ccait.ccweb.utils.FastJsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.query.Datetime;
import entity.tool.util.RequestUtils;
import org.quartz.DisallowConcurrentExecution;
import yizhit.workerlib.entites.CheckWorkceInfo;
import yizhit.workerlib.entites.ProjectInfo;
import yizhit.workerlib.interfaceuilt.FinalUtil;
import yizhit.workerlib.interfaceuilt.SHA256;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@DisallowConcurrentExecution
public class SelectQuartzCheckWorkceInfo {
    public void batchInsertCheckWorkceInfo() {
        // 数据库数据
        System.out.println("查询考勤表数据正在进入处理...");
        JSONObject params = new JSONObject();
        JSONArray array = null;
        try {
            //拼接校验码
            JSONObject jsonObject = new JSONObject();
            ProjectInfo projectInfo = new ProjectInfo();
            //查询工程ID
            List<ProjectInfo> projectInfoList = projectInfo.where("1=1").select(" project_id ").query(ProjectInfo.class);
            for (ProjectInfo projectInfoitem : projectInfoList) {
                //拼接密文
                StringBuilder sb = new StringBuilder();
                jsonObject.put("prjid", projectInfoitem.getEafId());
                String formatDate = Datetime.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                sb.append("appid=appid1").append("&data=" + jsonObject.toJSONString()).append("&format=json").append("&method=project.atte.list").append("&nonce=123456").append("&timestamp=" + formatDate).append("&version=1.0").append("&appsecret=123456");
                String hex = sb.toString().toLowerCase();
                System.out.println(hex);
                String s = SHA256.getSHA256StrJava(hex);

                //发送请求
                params.put("method", "project.atte.list");
                params.put("format", "json");
                params.put("version", "1.0");
                params.put("appid", "appid1");
                params.put("timestamp", formatDate);
                params.put("nonce", "123456");
                params.put("sign", s);
                params.put("data", jsonObject.toJSONString());
                String str = params.toJSONString();
                System.out.println("params:  " + str);
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
                String result = RequestUtils.post(FinalUtil.url, str, header);
                JSONObject json = JSONObject.parseObject(result);

                List<CheckWorkceInfo> checkWorkceInfosList = new ArrayList<CheckWorkceInfo>();// 数据获取正确
                if (json.containsKey("code") && json.get("code").equals("0")) {
                    array = json.getJSONObject("data").getJSONArray("list");
                    String text = array.toJSONString();
                    checkWorkceInfosList = FastJsonUtils.toList(text,   CheckWorkceInfo.class);
                    for (CheckWorkceInfo info : checkWorkceInfosList) {
                        try {
                            Integer i = info.insert();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("数据插入完成!");
                } else {
                    System.out.println("error:  " + result);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
