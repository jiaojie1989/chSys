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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Threads {
    private static boolean Inited = false;
    private static ScheduledExecutorService HealthReportor;
    private static ExecutorService PriceHandler;
    private static ExecutorService OrderHandler;
    private static ScheduledExecutorService SenderHandler;
    public static void Init() {
        HealthReportor = Executors.newScheduledThreadPool(1);
        PriceHandler = Executors.newFixedThreadPool(10);
        OrderHandler = Executors.newFixedThreadPool(3);
    }
}
