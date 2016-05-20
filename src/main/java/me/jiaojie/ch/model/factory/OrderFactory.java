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
package me.jiaojie.ch.model.factory;

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.OrderDetail;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.Price;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class OrderFactory {

    public static Order makeOrder(String project, Symbol symbol, String type, String price, String orderId) {
        OrderDetail detail = new OrderDetail();
        return new Order(ProjectFactory.getProject(project), detail, BuySellTypeFactory.getType(type), symbol, new Price(price));
    }
}
