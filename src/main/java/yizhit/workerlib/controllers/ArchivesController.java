package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzArvhivesInfo;

@RestController
public class ArchivesController {

    /**
     * 同步工程下的人员信息
     */
    @ResponseBody
    @AccessCtrl
    @RequestMapping(value = "apirchives/SynArchives",method = RequestMethod.GET)
    public  String  SynArchives(){
        SelectQuartzArvhivesInfo selectQuartzArvhivesInfo = new SelectQuartzArvhivesInfo();
        selectQuartzArvhivesInfo.batchInsertArvhivesInfo();
        return  "同步已成功";
    }






}
