package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzUnitrInfo;

@RestController
public class UnitInfoController {

    /**
     * 同步公司单位数据
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "unit/SynUnit",method = RequestMethod.GET)
    public  String  SynUnit(){
        SelectQuartzUnitrInfo selectQuartzUnitrInfo = new SelectQuartzUnitrInfo();
        selectQuartzUnitrInfo.batchInsertUnitrInfo();
        return  "同步已成功";
    }


}
