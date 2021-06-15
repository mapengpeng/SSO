package com.mapp.sso.client.controller;


import com.mapp.sso.core.SSOProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/403")
    public String forbid() {
        return "403";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        return "redirect:" + SSOProperties.CAS_SERVICE + "logout?service=" + SSOProperties.CLIENT_SERVICE;
    }
}
