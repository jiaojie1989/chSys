/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-06-01.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Mailer {

    private static HttpPost postUtil = new HttpPost("http://172.16.11.90/sendmail/mail.php");
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private final static String source = "模拟交易";
    public final static String exceptionTitle = "异常";
    public final static String infoTitle = "信息";
    public final static String errorTitle = "错误";
    public final static String alertTitle = "警告";
    public final static String users = "jiaojie";

    private static void sendMail(String title, String toUser, String msg) {
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("source", source));
            nvps.add(new BasicNameValuePair("title", title + " [" + getnowDate("yyyy-MM-dd HH:mm:ss") + "]"));
            nvps.add(new BasicNameValuePair("user", toUser));
            nvps.add(new BasicNameValuePair("msg", msg));
            postUtil.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));
            CloseableHttpResponse response = httpClient.execute(postUtil);
            System.out.println(response.getStatusLine());
            System.out.println(response);
            HttpEntity entity = response.getEntity();
            System.out.println(entity);
            EntityUtils.consume(entity);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void sendErrorMail(Object msg, String toUser, String titleExtra) {
        sendMail(titleExtra + "_" + errorTitle, toUser, msg.toString());
    }

    public static void sendErrorMail(Object msg, String toUser) {
        sendMail(errorTitle, toUser, msg.toString());
    }

    public static void sendErrorMail(Object msg) {
        sendMail(errorTitle, users, msg.toString());
    }

    public static void sendExceptionMail(Object msg, String toUser, String titleExtra) {
        sendMail(titleExtra + "_" + exceptionTitle, toUser, msg.toString());
    }

    public static void sendExceptionMail(Object msg, String toUser) {
        sendMail(exceptionTitle, toUser, msg.toString());
    }

    public static void sendExceptionMail(Object msg) {
        sendMail(exceptionTitle, users, msg.toString());
    }

    public static void sendInfoMail(Object msg, String toUser, String titleExtra) {
        sendMail(titleExtra + "_" + infoTitle, toUser, msg.toString());
    }

    public static void sendInfoMail(Object msg, String toUser) {
        sendMail(infoTitle, toUser, msg.toString());
    }

    public static void sendInfoMail(Object msg) {
        sendMail(infoTitle, users, msg.toString());
    }

    public static void sendAlertMail(Object msg, String toUser, String titleExtra) {
        sendMail(titleExtra + "_" + alertTitle, toUser, msg.toString());
    }

    public static void sendAlertMail(Object msg, String toUser) {
        sendMail(alertTitle, toUser, msg.toString());
    }

    public static void sendAlertMail(Object msg) {
        sendMail(alertTitle, users, msg.toString());
    }

    /**
     * @deprecated @param Utf8String
     * @return
     */
    @Deprecated
    public static String toGBK(String Utf8String) {
        try {
            String utf8 = new String(Utf8String.getBytes("UTF-8"));
            System.out.println(utf8);
            String unicode = new String(utf8.getBytes(), "UTF-8");
            System.out.println(unicode);
            String ret = new String(unicode.getBytes("GBK"));
            System.out.println(ret);
            return ret;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 根据样式格式化时间 "yyyyMMdd" "yyyyMMddHHmmss" "yyyyMMddHHmmssssssss"
     *
     * @param dateFormat
     * @return
     */
    public static String getnowDate(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }
}
