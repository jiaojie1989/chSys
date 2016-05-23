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

import java.util.Objects;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Order {

    protected Project project;
    protected OrderDetail detail;
    protected BuySellType type;
    protected Symbol symbol;
    protected Symbol succSymbol = null;
    protected Price price;
    protected boolean deal = false;
    protected boolean canDeal = false;

    public Order(Project project, OrderDetail detail, BuySellType type, Symbol symbol, Price price) {
        this.project = project;
        this.detail = detail;
        this.type = type;
        this.symbol = symbol;
        this.price = price;
        this.canDeal = this.symbol.canDeal(type, price);
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public void setSucc(Symbol dealSymbol) {
        this.succSymbol = dealSymbol;
        this.deal = true;
    }

    public boolean isDeal() {
        return this.deal;
    }

    public boolean canDeal() {
        return this.canDeal;
    }

    public Price getOrderPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "" + this.detail;
    }

    @Override
    public int hashCode() {
        return this.detail.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return Objects.equals(this.detail, other.detail);
    }
}
