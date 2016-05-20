/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-19.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service;

import java.math.BigDecimal;
import java.util.Hashtable;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.OrderList;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Project {

    protected String[] Name;
    protected Hashtable<String[], Symbol> Prices;
    protected Hashtable<String[], OrderList> OrderLists;

    public Project(String[] Name) {
        this.Name = Name;
    }
    
    public void SetPrice() {
    
    }

}
