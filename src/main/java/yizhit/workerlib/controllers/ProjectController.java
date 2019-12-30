package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import ccait.ccweb.controllers.BaseController;
import ccait.ccweb.model.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import yizhit.workerlib.timer.SelectQuartzProjectInfo;

@RestController
public class ProjectController extends BaseController {

        /**
         * 同步工程信息
         */
        @ResponseBody
        @AccessCtrl
        @RequestMapping(value = "project/SynProject",method = RequestMethod.POST)
        public ResponseData  SynAlluser(){
            SelectQuartzProjectInfo selectQuartzProjectInfof= new SelectQuartzProjectInfo();
            selectQuartzProjectInfof.batchInsertProjectInfo();
            return success("同步已成功");
       }

}
