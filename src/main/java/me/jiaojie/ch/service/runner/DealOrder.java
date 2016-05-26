/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-26.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service.runner;

import me.jiaojie.ch.model.basic.Order;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class DealOrder implements Runnable {

    private final Order order;

    public DealOrder(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
    }

}
