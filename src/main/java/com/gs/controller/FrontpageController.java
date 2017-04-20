package com.gs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 郭昶 on 2017/4/12.
 * 链接到前台页面
 */

@Controller
@RequestMapping("/")
public class FrontpageController {

    @RequestMapping(value ="platformIntro",method = RequestMethod.GET)
    public String Goplatform(){
        return "Frontpage/PlatformIntro";
    }


}
