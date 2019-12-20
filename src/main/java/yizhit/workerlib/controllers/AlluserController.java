package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzAllUserInfo;

@RestController
public class AlluserController {
    /**
     * 同步所有员工信息
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "alluser/SynAlluser",method = RequestMethod.GET)
    public  String  SynAlluser(){
        SelectQuartzAllUserInfo userInfo = new SelectQuartzAllUserInfo();
        userInfo.batchInsertArchivesInfo();
        return  "同步已成功";

    }



}
