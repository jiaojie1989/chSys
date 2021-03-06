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
package me.jiaojie.ch.model.project;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.basic.Symbol;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Hk extends Trade {

    private static Hk instance;
    private int initNum;
    private static LinkedBlockingQueue<Order> orderList;

    protected Hk() {
        this.project = new Project("hk");
        this.initNum = 5000;
        this.init();
    }

    @Override
    protected void init() {
        this.buyOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        this.sellOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        this.buyOrderMap = new ConcurrentHashMap<String, TreeSet<Order>>(this.initNum);
        this.sellOrderMap = new ConcurrentHashMap<String, TreeSet<Order>>(this.initNum);
        this.priceMap = new ConcurrentHashMap<String, Symbol>(this.initNum);
        this.buyOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        this.sellOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        orderList = new LinkedBlockingQueue<Order>();
    }

    public static Hk getInstance() {
        if (instance == null) {
            synchronized (Hk.class) {
                if (instance == null) {
                    instance = new Hk();
                }
            }
        }
        return instance;
    }

    @Override
    public int getListNum() {
        return orderList.size();
    }

    @Override
    public Order getSuccOrder() throws InterruptedException, NullPointerException {
        return orderList.poll(100, TimeUnit.MICROSECONDS);
    }

    @Override
    public boolean addSuccOrder(Order order) {
        return orderList.offer(order);
    }
}
