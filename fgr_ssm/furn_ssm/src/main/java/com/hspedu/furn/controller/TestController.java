package com.hspedu.furn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@Controller
public class TestController {

    @RequestMapping("/hi")
    public String hi() {
        return "hi";
    }
}
