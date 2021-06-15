package com.mapp.sso.server.manager;

import cn.hutool.core.collection.CollUtil;
import com.mapp.sso.server.service.WebApplicationService;
import com.mapp.sso.server.ticket.AbstractTicket;
import com.mapp.sso.server.ticket.Ticket;
import com.mapp.sso.server.ticket.TicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;

/**
 * ticket校验定时器
 *
 * @author mapp
 */
@Configuration
@EnableScheduling
public class TicketSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(TicketSchedule.class);

    @Autowired
    private LogoutManager logoutManager;
    @Autowired
    private TicketRegistry ticketRegistry;

    /**
     * 十分钟校验一次ticket
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void validateTicket() {
        Collection<Ticket> tickets = ticketRegistry.getTickets();
        if (CollUtil.isNotEmpty(tickets)) {
            LOG.info("begin validateTicket ... ticket size: {}", tickets.size());
            for (Ticket ticket : tickets) {
                if (ticket instanceof AbstractTicket) {
                    if (((AbstractTicket) ticket).validate()) {
                        ticketRegistry.deleteTicket(ticket.getId());
                        logoutManager.logout(ticket.getId());
                    }
                }
            }
        }
    }
}
