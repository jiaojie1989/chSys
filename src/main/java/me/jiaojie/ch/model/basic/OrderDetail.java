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

import java.util.Objects;
import me.jiaojie.ch.service.MyLogger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class OrderDetail {

    private String orderId = "";
    private String sid;
    private int amount;
    private long timestamp;

    public OrderDetail(String orderId, String sid, int amount, long timestamp) {
        this.orderId = orderId;
        this.sid = sid;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public OrderDetail() {
        this.orderId = "_0_";
        this.sid = "110";
        this.amount = 12311231;
        this.timestamp = 1;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return this.orderId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderDetail other = (OrderDetail) obj;
        if (!Objects.equals(this.orderId, other.getOrderId())) {
            return false;
        }
        return true;
    }
}
