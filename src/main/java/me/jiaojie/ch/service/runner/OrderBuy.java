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
package me.jiaojie.ch.service.runner;

import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.OrderDetail;
import me.jiaojie.ch.model.basic.OrderJsonObj;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.factory.BuySellTypeFactory;
import me.jiaojie.ch.model.factory.ProjectFactory;
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.model.project.Hk;
import me.jiaojie.ch.model.project.Trade;
import me.jiaojie.ch.model.project.Us;
import me.jiaojie.ch.service.MyLogger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class OrderBuy implements Runnable {

    protected Order order;
    protected String projectName;
    protected OrderJsonObj orderObj;

    public OrderBuy(OrderJsonObj orderObj, String projectName) {
        this.orderObj = orderObj;
        this.projectName = projectName;
    }

    @Override
    public void run() {
        Trade project;
        switch (projectName) {
            case "cn":
                project = Cn.getInstance();
                break;
            case "us":
                project = Us.getInstance();
                break;
            case "hk":
                project = Hk.getInstance();
                break;
            default:
                project = null;
                break;
        }

        if (null != project) {
            Symbol symbol = project.getSymbol(orderObj.getSymbol());
            order = new Order(ProjectFactory.getProject(this.projectName), new OrderDetail(orderObj.getOrderId(), orderObj.getSid(), orderObj.getAmount(), orderObj.getTimestamp()), BuySellTypeFactory.getType("buy"), symbol, new Price(orderObj.getPrice()), this.orderObj.getTimestamp());

            if (orderObj.getWait() == 1) {
//                MyLogger.info("Queued Buy: " + order);
                project.mkBuyOrder(order);
            } else {
                if (order.canDeal()) {
//                    MyLogger.info("Succ Buy: " + order);
                    order.setSucc(symbol);
                    project.addSuccOrder(order);
                } else {
//                    MyLogger.info("Queued Buy: " + order);
                    project.mkBuyOrder(order);
                }
            }
        }
    }
}
