package com.mapp.sso.server.controller;


import com.mapp.sso.server.ticket.TicketRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private TicketRegistry ticketRegistry;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "loginIndex";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getAttribute("errMsg") != null) {
            return "loginIndex";
        }
        return "redirect:/";
    }
}
