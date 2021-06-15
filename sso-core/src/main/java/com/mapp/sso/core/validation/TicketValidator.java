package com.mapp.sso.core.validation;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mapp.sso.core.SSOProperties;
import com.mapp.sso.core.authentication.Principal;
import com.mapp.sso.core.authentication.SimplePrincipal;
import com.mapp.sso.core.exception.TicketValidateException;

import java.util.HashMap;
import java.util.Map;

/**
 * 票据校验器
 *
 * @author mapp
 */
public class TicketValidator {

    public Principal validateTiet(String ticket) throws TicketValidateException {

            Map<String, Object> parms = new HashMap<>();
            parms.put("service", SSOProperties.CLIENT_SERVICE);
            parms.put("ticket", ticket);

            String res = HttpUtil.post(SSOProperties.CAS_SERVICE + "validateServiceTicket", parms);
            return JSONUtil.toBean(res, SimplePrincipal.class);

    }
}
