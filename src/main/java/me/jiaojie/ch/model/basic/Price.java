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
public class Price {

    protected double price;

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
        return queryPrice.getPrice() <= this.price;
    }

    public boolean isNoLessThan(Price queryPrice) {
        return queryPrice.getPrice() >= this.price;
    }

    @Override
    public String toString() {
        return String.valueOf(this.price);
    }
}
