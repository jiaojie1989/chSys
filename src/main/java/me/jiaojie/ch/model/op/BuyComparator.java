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
package me.jiaojie.ch.model.op;

import java.util.Comparator;
import me.jiaojie.ch.model.basic.Order;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class BuyComparator implements Comparator<Order> {

    @Override
    public int compare(Order A, Order B) {
        if (A.getOrderPrice().isNoLessThan(B.getOrderPrice())) {
            return 1;
        } else {
            return -1;
        }
    }
}