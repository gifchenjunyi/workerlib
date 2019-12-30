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
public class ArchivesController extends BaseController {

    /**
     * 同步工程下的人员信息
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "apirchives/SynArchives",method = RequestMethod.GET)
    public ResponseData SynArchives(){
        SelectQuartzArvhivesInfo selectQuartzArvhivesInfo = new SelectQuartzArvhivesInfo();
        selectQuartzArvhivesInfo.batchInsertArvhivesInfo();
        return success("同步已成功");
    }






}
