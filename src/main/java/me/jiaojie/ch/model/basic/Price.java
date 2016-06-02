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

import me.jiaojie.ch.service.MyLogger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Price {

    private double price;

    public Price(double price) {
        this.price = price;
    }

    public Price(String price) {
        this.price = Double.valueOf(price);
    }

    public double getPrice() {
        return this.price;
    }

    public boolean isNoMoreThan(Price queryPrice) {
//        MyLogger.debug("query<=this query " + queryPrice.getPrice() + " - this " + this.getPrice());
        return queryPrice.getPrice() <= this.getPrice();
    }

    public boolean isNoLessThan(Price queryPrice) {
//        MyLogger.debug("query>=this query " + queryPrice.getPrice() + " - this " + this.getPrice());
        return queryPrice.getPrice() >= this.getPrice();
    }

    @Override
    public String toString() {
        return String.valueOf(this.getPrice());
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
