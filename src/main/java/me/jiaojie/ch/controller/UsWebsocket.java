/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-31.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.controller;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.model.project.Trade;
import me.jiaojie.ch.model.project.Us;
import me.jiaojie.ch.service.Mailer;
import me.jiaojie.ch.service.Threads;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
@ServerEndpoint("/us/result")
public class UsWebsocket {

    private static Set<Session> session_list = null;
    private static boolean linkStatus = false;

    public UsWebsocket() {
        if (null == session_list) {
            Threads.Init();
            session_list = new TreeSet<Session>();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        synchronized (session_list) {
            session_list = session.getOpenSessions();
        }
        System.out.println(session_list);
        if (message.toUpperCase().equals("PING")) {
            try {
                session.getBasicRemote().sendText("PONG");
            } catch (Exception e) {
            }
        } else {
            try {
                Trade project = Us.getInstance();
                Order order = project.getSuccOrder();
                System.out.println("Order: " + order);
                while (null != order) {
                    for (Session s : session_list) {
                        if (s.isOpen()) {
                            try {
                                s.getBasicRemote().sendText(JSON.toJSONString(order));
                            } catch (Exception e) {
                                Mailer.sendErrorMail(e.getMessage(), Mailer.users, "UsSocket发送失败");
                            }
                        }
                    }
                    order = project.getSuccOrder();
                }
            } catch (Exception e) {
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        synchronized (session_list) {
            session_list = session.getOpenSessions();
            linkStatus = true;
        }
        System.out.println("Client connected");
        session.getBasicRemote().sendText("Welcome! Total clients " + session_list.size() + ".\r\n\r\n");

        try {
            Trade project = Cn.getInstance();
            Order order = project.getSuccOrder();
            System.out.println("Order: " + order);
            if (null != order) {
                session_list.stream().filter((s) -> (s.isOpen())).forEach((s) -> {
                    try {
                        s.getBasicRemote().sendText(JSON.toJSONString(order));
                    } catch (Exception e) {
                        Mailer.sendErrorMail(e.getMessage(), Mailer.users, "UsSocket发送失败");
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    @OnClose
    public void onClose(Session session) {
        synchronized (session_list) {
            session_list = session.getOpenSessions();
            if (session_list.size() == 0) {
                linkStatus = false;
            }
        }
        System.out.println("Connection closed");

    }

    public static Set<Session> getOpenSessions() {
        return session_list;
    }

    public static boolean getLinkStatus() {
        return linkStatus;
    }
}
