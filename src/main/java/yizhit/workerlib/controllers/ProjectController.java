package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

        /**
         * 同步工程信息
         */
        @ResponseBody
        @AccessCtrl
        @RequestMapping(value = "project/SynProject",method = RequestMethod.GET)
        public  String  SynAlluser(){
//            SelectQuartzProjectInfof selectQuartzProjectInfof= new SelectQuartzProjectInfof();
//            selectQuartzProjectInfof.batchInsertProjectInfo();
            return  "同步已成功";
       }

}
