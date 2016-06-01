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
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.model.project.Hk;
import me.jiaojie.ch.model.project.Trade;
import me.jiaojie.ch.model.project.Us;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class DealOrder implements Runnable {

    private final Order order;
    private final String projectName;

    public DealOrder(Order order, String projectName) {
        this.order = order;
        this.projectName = projectName;
    }

    @Override
    public void run() {
        Trade cn;
        switch (projectName) {
            case "cn":
                cn = Cn.getInstance();
                break;
            case "us":
                cn = Us.getInstance();
                break;
            case "hk":
                cn = Hk.getInstance();
                break;
            default:
                cn = null;
                break;
        }

        if (null != cn) {
            cn.addSuccOrder(order);
        }
    }
}
