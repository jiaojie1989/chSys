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

import com.alibaba.fastjson.JSON;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.websocket.Session;
import me.jiaojie.ch.controller.CnWebsocket;
import me.jiaojie.ch.controller.HkWebsocket;
import me.jiaojie.ch.controller.UsWebsocket;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.model.project.Hk;
import me.jiaojie.ch.model.project.Us;

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
    private static ScheduledThreadPoolExecutor CnWebSocketHandler;
    private static ScheduledThreadPoolExecutor HkWebSocketHandler;
    private static ScheduledThreadPoolExecutor UsWebSocketHandler;

    private static void runCnSocket() {
        CnWebSocketHandler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Cn cn = Cn.getInstance();
                    int i = 0;
                    while (i < 100) {
                        i++;
                        if (cn.getListNum() == 0) {
                            continue;
                        } else {
                            Set<Session> sessionList = CnWebsocket.getOpenSessions();
                            if (sessionList.size() > 0) {
                                Order order = cn.getSuccOrder();
                                sessionList.stream().filter((s) -> (s.isOpen())).forEach((s) -> {
                                    try {
                                        s.getBasicRemote().sendText(JSON.toJSONString(order));
                                    } catch (Exception e) {
                                        Mailer.sendErrorMail(e.getMessage(), Mailer.users, "CnSocket发送失败");
                                    }
                                });
                            } else {
                                // do nothing
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "CnSocket-InterruptedException");
                } catch (NullPointerException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "CnSocket-NullPointerException");
                }
            }
        }, 10, 1, TimeUnit.SECONDS);
    }

    private static void runHkSocket() {
        HkWebSocketHandler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Hk cn = Hk.getInstance();
                    int i = 0;
                    while (i < 100) {
                        i++;
                        if (cn.getListNum() == 0) {
                            continue;
                        } else {
                            Set<Session> sessionList = HkWebsocket.getOpenSessions();
                            if (sessionList.size() > 0) {
                                Order order = cn.getSuccOrder();
                                sessionList.stream().filter((s) -> (s.isOpen())).forEach((s) -> {
                                    try {
                                        s.getBasicRemote().sendText(JSON.toJSONString(order));
                                    } catch (Exception e) {
                                        Mailer.sendErrorMail(e.getMessage(), Mailer.users, "HkSocket发送失败");
                                    }
                                });
                            } else {
                                // do nothing
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "HkSocket-InterruptedException");
                } catch (NullPointerException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "HkSocket-NullPointerException");
                }
            }
        }, 10, 1, TimeUnit.SECONDS);
    }

    private static void runUsSocket() {
        UsWebSocketHandler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                MyLogger.debug("running ussocket");
                try {
                    Us cn = Us.getInstance();
                    int i = 0;
                    while (i < 100) {
                        i++;
                        if (cn.getListNum() == 0) {
                            continue;
                        } else {
                            System.out.println("Total Num: " + cn.getListNum());
                            if (UsWebsocket.getLinkStatus()) {
                                Set<Session> sessionList = UsWebsocket.getOpenSessions();
                                System.out.println("Connected Clients: " + sessionList.size());
                                if (sessionList.size() > 0) {
                                    Order order = cn.getSuccOrder();
//                                    System.out.println("Order: " + order);
                                    sessionList.stream().filter((s) -> (s.isOpen())).forEach((s) -> {
                                        try {
                                            s.getBasicRemote().sendText(JSON.toJSONString(order));
                                        } catch (Exception e) {
                                            Mailer.sendErrorMail(e.getMessage(), Mailer.users, "UsSocket发送失败");
                                        }
                                    });
                                } else {
                                    // do nothing
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "UsSocket-InterruptedException");
                } catch (NullPointerException e) {
                    Mailer.sendExceptionMail(e.getMessage(), Mailer.users, "UsSocket-NullPointerException");
                } finally {
//                    MyLogger.debug("end running ussocket");
                }
            }
        }, 10, 1, TimeUnit.SECONDS);
    }

    public static void Init() {
        if (Inited) {
        } else {
            synchronized (Threads.class) {
                if (Inited) {
                } else {
                    HealthReportor = Executors.newScheduledThreadPool(1);
                    PriceHandler = Executors.newFixedThreadPool(4);
                    PriceScanner = Executors.newFixedThreadPool(8);
                    OrderHandler = Executors.newFixedThreadPool(2);
                    OrderSuccHandler = Executors.newFixedThreadPool(1);
                    TestHandler = new ScheduledThreadPoolExecutor(1);
                    CnWebSocketHandler = new ScheduledThreadPoolExecutor(1);
                    HkWebSocketHandler = new ScheduledThreadPoolExecutor(1);
                    UsWebSocketHandler = new ScheduledThreadPoolExecutor(1);
                    runCnSocket();
                    runUsSocket();
                    runHkSocket();
//                TestHandler.scheduleAtFixedRate(new Runnable() {
//                    @Override
//                    public void run() {
//                        Set<Session> sessionList = CnWebsocket.getOpenSessions();
//                        if (sessionList.size() > 0) {
//                            sessionList.stream().filter((s) -> (s.isOpen())).forEach((s) -> {
//                                try {
//                                    s.getBasicRemote().sendText("Ack 当前总人数[" + sessionList.size() + "]");
//                                } catch (Exception e) {
//                                    // do nothing
//                                }
//                            });
//                        } else {
//                            MyLogger.info("Session List Empty!");
//                        }
//                    }
//                }, 1, 3, TimeUnit.SECONDS);
                    Inited = true;
                    Mailer.sendInfoMail("System has started! @" + Mailer.getnowDate("yyyy-MM-dd HH:mm:ss"), Mailer.users, "StartUp");
                }
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
