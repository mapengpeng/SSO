package com.mapp.sso.server.ticket;

import java.util.Collection;

/**
 * 票根接口
 *
 * @author mapp
 * @date 2021-6-4
 */
public interface TicketRegistry {

    /**
     * 保存
     * @param ticket
     */
    void addTicket(Ticket ticket);

    /**
     * 获取
     * @param id
     * @return
     */
    Ticket getTicket(String id);

    /**
     * 泛型获取
     * @param id
     * @param tClass
     * @param <T>
     * @return
     */
    <T extends Ticket> T getTicket(String id, Class<T> tClass);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteTicket(String id);

    /**
     * 获取所有的票根
     * @return
     */
    Collection<Ticket> getTickets();
}
