package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import ccait.ccweb.controllers.BaseController;
import ccait.ccweb.model.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzArvhivesInfo;

@RestController
public class UnitInfoController extends BaseController {

    /**
     * 同步公司单位数据
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "unit/SynUnit",method = RequestMethod.POST)
    public ResponseData SynUnit(){
        SelectQuartzArvhivesInfo selectQuartzArvhivesInfo = new SelectQuartzArvhivesInfo();
        selectQuartzArvhivesInfo.batchInsertArvhivesInfo();
        return success("同步已成功");
    }


}
