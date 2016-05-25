/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-23.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.model.project;

import java.util.concurrent.ConcurrentHashMap;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.basic.Project;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Cn extends Trade {

    protected static Cn instance;
    protected int initNum;
    private static final Logger LOG = Logger.getLogger(Cn.class.getName());

    protected Cn() {
        this.project = new Project("cn");
        this.initNum = 5000;
        this.init();
    }

    @Override
    protected void init() {
        this.buyOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        this.sellOrderLock = new ConcurrentHashMap<String, Boolean>(this.initNum);
        this.buyOrderMap = new ConcurrentHashMap<String, TreeSet<Order>>(this.initNum);
        this.sellOrderMap = new ConcurrentHashMap<String, TreeSet<Order>>(this.initNum);
        this.priceMap= new ConcurrentHashMap<String, Symbol>(this.initNum);
    }

    public static Cn getInstance() {
        if (instance == null) {
            synchronized (Cn.class) {
                if (instance == null) {
                    instance = new Cn();
                }
            }
        }
        return instance;
    }

}
