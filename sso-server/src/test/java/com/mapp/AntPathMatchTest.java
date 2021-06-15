package com.mapp;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class AntPathMatchTest {

    @Test
    public void test() {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        System.out.println(pathMatcher.match("/test", "/test"));
        System.out.println(pathMatcher.match("/test/**", "/test/jj"));
        System.out.println(pathMatcher.match("/**", "/test/jj"));
    }
}
