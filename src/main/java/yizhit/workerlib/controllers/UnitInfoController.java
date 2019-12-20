package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzArvhivesInfo;

@RestController
public class UnitInfoController {

    /**
     * 同步公司单位数据
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "unit/SynUnit",method = RequestMethod.POST)
    public  String  SynUnit(){
        SelectQuartzArvhivesInfo selectQuartzArvhivesInfo = new SelectQuartzArvhivesInfo();
        selectQuartzArvhivesInfo.batchInsertArvhivesInfo();
        return  "同步已成功";
    }


}
