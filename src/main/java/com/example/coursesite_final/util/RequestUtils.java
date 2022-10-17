package com.example.coursesite_final.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("USER-AGENT");
    }


    public static String getUserIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
