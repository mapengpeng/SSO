package com.mapp.sso.server.controller;

import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.exception.SSOException;
import com.mapp.sso.core.util.WebUtil;
import com.mapp.sso.server.ticket.ServiceTicket;
import com.mapp.sso.server.ticket.TicketRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 票据校验接口
 *
 * @author mapp
 */
@RestController
public class SSOServiceController {

    @Autowired
    private TicketRegistry ticketRegistry;

    @RequestMapping("/validateServiceTicket")
    public Principal validateServiceTicket(HttpServletRequest request) {
        String ticket = WebUtil.getCleanParam(request, "ticket");
        String service = WebUtil.getCleanParam(request, "service");

        if (ticket == null) {
            throw new SSOException("ticket is null");
        }
        if (service == null) {
            throw new SSOException("service is null");
        }

        ServiceTicket st = ticketRegistry.getTicket(ticket, ServiceTicket.class);
        if (st == null) {
            throw new SSOException("illegal ticket !");
        }

        if (!st.validateService(service)) {
            throw new SSOException("invalid ticket !");
        }

        return st.getTgtTicket().getPrincipal();
    }
}
