/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-19.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.model.basic;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class BuySellType {

    protected int type;

    public BuySellType(int type) throws RuntimeException {
        if (type != 1 && type != -1) {
            throw new RuntimeException("BuySell Type Error");
        }
        this.type = type;
    }

    public boolean isBuyType() {
        return 1 == this.type;
    }

    public boolean isSellType() {
        return -1 == this.type;
    }

    @Override
    public String toString() {
        return 1 == type ? "buy" : "sell";
    }

    @Override
    public int hashCode() {
        return this.type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BuySellType other = (BuySellType) obj;
        return this.type == other.type;
    }
}
