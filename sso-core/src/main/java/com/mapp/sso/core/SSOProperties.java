package com.mapp.sso.core;


import cn.hutool.core.io.resource.ResourceUtil;
import com.mapp.sso.core.exception.SSOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSOProperties {

    private static final Logger LOG = LoggerFactory.getLogger(SSOProperties.class);

    public static String CAS_SERVICE;
    public static String CLIENT_SERVICE;
    public static String IGNORE_URL;

    static {
        try (InputStream stream = ResourceUtil.getStream("sso.properties");) {
            Properties p = new Properties();
            p.load(stream);
            CAS_SERVICE = p.getProperty("cas.service");
            CLIENT_SERVICE = p.getProperty("client.service");
            IGNORE_URL = p.getProperty("client.ignoreurl");
            checkProperties();
        } catch (FileNotFoundException e) {
            LOG.error("sso.properties配置文件不存在！");
        } catch (IOException e) {
            LOG.error("sso.properties配置文件加载读取！");
        }
    }

    private static void checkProperties() {
        if (CAS_SERVICE == null) {
            throw new SSOException("cas.service属性 未配置！");
        }
        if (CLIENT_SERVICE == null) {
            throw new SSOException("cas.service属性 未配置！");
        }
        if (CAS_SERVICE.lastIndexOf("/") == -1) {
            CAS_SERVICE = CAS_SERVICE + "/";
        }
    }
}
