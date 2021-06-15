package com.mapp.sso.server.ticket;


import com.mapp.sso.core.authentication.Principal;

/**
 * TGT
 *
 * @author mapp
 */
public class TgtTicket extends AbstractTicket {

    private Principal principal;

    public TgtTicket() {
    }

    public TgtTicket(String id, Long timeOut) {
        super(id, timeOut);
    }

    public TgtTicket(String id, Long timeOut, Principal principal) {
        super(id, timeOut);
        this.principal = principal;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
