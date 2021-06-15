package com.mapp.sso.server.ticket;


import java.io.Serializable;
import java.util.Map;


/**
 * Ticket
 *
 * @author mapp
 */
public interface Ticket extends Serializable {

    String getId();

    Map<String, Object> getAttributes();
}
