/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-24.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.model.basic;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class PriceJsonObj {

    public double bid;
    public double ask;

    @Override
    public String toString() {
        return "bid:" + bid + ";ask:" + ask;
    }
}
