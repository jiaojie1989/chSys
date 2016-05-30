/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-20.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.websocket.Session;
import me.jiaojie.ch.controller.CnWebsocket;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Threads {

    private static boolean Inited = false;
    private static ScheduledExecutorService HealthReportor;
    private static ExecutorService PriceHandler;
    private static ExecutorService PriceScanner;
    private static ExecutorService OrderHandler;
    private static ExecutorService OrderSuccHandler;
    private static ScheduledExecutorService SenderHandler;
    private static ScheduledThreadPoolExecutor TestHandler;

    public static void Init() {
        if (Inited) {
        } else {
            synchronized (Threads.class) {
                HealthReportor = Executors.newScheduledThreadPool(1);
                PriceHandler = Executors.newFixedThreadPool(4);
                PriceScanner = Executors.newFixedThreadPool(8);
                OrderHandler = Executors.newFixedThreadPool(2);
                OrderSuccHandler = Executors.newFixedThreadPool(1);
                TestHandler = new ScheduledThreadPoolExecutor(1);
                TestHandler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Set<Session> sessionList = CnWebsocket.getOpenSessions();
                        if (sessionList.size() > 0) {
                            for (Session s : sessionList) {
                                if (s.isOpen()) {
                                    try {
                                        s.getBasicRemote().sendText("Ack 当前总人数[" + sessionList.size() + "]");
                                    } catch (Exception e) {
                                        // do nothing
                                    }
                                }
                            }
                        } else {
                            MyLogger.info("Session List Empty!");
                        }
                    }
                }, 1, 3, TimeUnit.SECONDS);
                Inited = true;
            }
        }
    }

    public static ScheduledExecutorService getHealthReportor() {
        return HealthReportor;
    }

    public static ExecutorService getPriceHandler() {
        return PriceHandler;
    }

    public static ExecutorService getPriceScanner() {
        return PriceScanner;
    }

    public static ExecutorService getOrderHandler() {
        return OrderHandler;
    }

    public static ExecutorService getOrderSuccHandler() {
        return OrderSuccHandler;
    }

    public static ScheduledThreadPoolExecutor getTestHandler() {
        return TestHandler;
    }

}
