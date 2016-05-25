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
    private static ExecutorService PriceScanner;
    private static ExecutorService OrderHandler;
    private static ScheduledExecutorService SenderHandler;

    public static void Init() {
        if (Inited) {
        } else {
            synchronized (Threads.class) {
                HealthReportor = Executors.newScheduledThreadPool(1);
                PriceHandler = Executors.newFixedThreadPool(4);
                PriceScanner = Executors.newFixedThreadPool(8);
                OrderHandler = Executors.newFixedThreadPool(2);
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
    
}
