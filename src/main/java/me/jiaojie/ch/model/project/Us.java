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
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.basic.Symbol;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Us extends Trade {

    private static Us instance;
    private int initNum;

    protected Us() {
        this.project = new Project("us");
        this.initNum = 8000;
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
    }

    public static Us getInstance() {
        if (instance == null) {
            synchronized (Us.class) {
                if (instance == null) {
                    instance = new Us();
                }
            }
        }
        return instance;
    }
}
