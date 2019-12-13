package yizhit.workerlib.controllers;

import ccait.ccweb.annotation.AccessCtrl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestttController {
    @ResponseBody
    @AccessCtrl
    @RequestMapping( value = "asyncapi/testtt", method = RequestMethod.GET  )
    public String doGet()  {
        return "success";
    }
}
