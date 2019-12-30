package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import ccait.ccweb.controllers.BaseController;
import ccait.ccweb.model.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzAllUserInfo;

@RestController
public class AlluserController extends BaseController {
    /**
     * 同步所有员工信息
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "alluser/SynAlluser",method = RequestMethod.GET)
    public ResponseData SynAlluser(){
        SelectQuartzAllUserInfo userInfo = new SelectQuartzAllUserInfo();
        userInfo.batchInsertArchivesInfo();
        return success("同步已成功");

    }



}
