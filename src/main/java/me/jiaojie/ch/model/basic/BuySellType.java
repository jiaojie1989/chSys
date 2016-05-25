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

import java.util.logging.Logger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class BuySellType {

    private int type;

    public BuySellType(int type) throws RuntimeException {
        if (type != 1 && type != -1) {
            throw new RuntimeException("BuySell Type Error");
        }
        this.type = type;
    }

    public boolean isBuyType() {
        return 1 == this.getType();
    }

    public boolean isSellType() {
        return -1 == this.getType();
    }

    @Override
    public String toString() {
        return 1 == getType() ? "buy" : "sell";
    }

    @Override
    public int hashCode() {
        return this.getType();
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
        return this.getType() == other.getType();
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
}
