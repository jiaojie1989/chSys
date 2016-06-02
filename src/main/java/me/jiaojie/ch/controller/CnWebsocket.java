/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-30.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import me.jiaojie.ch.service.Threads;

/**
 * A股websocket数据接口
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
@ServerEndpoint("/cn/result")
public class CnWebsocket {

    private static Set<Session> session_list = null;
    private static boolean linkStatus = false;

    public CnWebsocket() {
        if (null == session_list) {
            Threads.Init();
            session_list = new TreeSet<Session>();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        synchronized (session_list) {
            session_list = session.getOpenSessions();
            linkStatus = true;
        }
        if (message.toUpperCase().equals("PING")) {
            try {
                session.getBasicRemote().sendText("PONG");
            } catch (Exception e) {
            }
        } else {
            for (Session s : session_list) {
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText("Total [" + session_list.size() + "]---" + session.getId() + " Says:" + message);
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(session);
        synchronized (session_list) {
            session_list = session.getOpenSessions();
            linkStatus = true;
        }
        System.out.println("Client connected");
        session.getBasicRemote().sendText("Welcome! Total clients " + session_list.size() + ".\r\n\r\n");
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
