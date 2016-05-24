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
package me.jiaojie.ch.model.basic;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class OrderDetail {

    protected String orderId;
    protected String sid;
    protected int amount;
    protected long timestamp;

    public OrderDetail(String orderId, String sid, int amount, long timestamp) {
        this.orderId = orderId;
        this.sid = sid;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public OrderDetail() {

    }
}
