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

    private Project project;
    private OrderDetail detail;
    private BuySellType type;
    private Symbol symbol;
    private Symbol succSymbol = null;
    private Price price;
    private boolean deal = false;
    private boolean canDeal = false;
    private long timestamp;
    final private static long timeDiff = 180L; // 3 min

    public Order(Project project, OrderDetail detail, BuySellType type, Symbol symbol, Price price) {
        this.project = project;
        this.detail = detail;
        this.type = type;
        this.symbol = symbol;
        this.price = price;
        this.timestamp = System.currentTimeMillis() / 1000;
        this.canDeal = this.canDeal(type, price);
    }
    
    public Order(Project project, OrderDetail detail, BuySellType type, Symbol symbol, Price price, long timestamp) {
        this.project = project;
        this.detail = detail;
        this.type = type;
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
        this.canDeal = this.canDeal(type, price);
    }

    protected boolean canDeal(BuySellType type, Price price) {
        if (this.timestamp > this.symbol.getTimestamp() + timeDiff) {
            return false;
        } else {
            return this.symbol.canDeal(type, price);
        }
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public void setSucc(Symbol dealSymbol) {
        this.setSuccSymbol(dealSymbol);
        this.setDeal(true);
    }

    public boolean isDeal() {
        return this.deal;
    }

    public boolean canDeal() {
        return this.isCanDeal();
    }

    public Price getOrderPrice() {
        return this.getPrice();
    }

    @Override
    public String toString() {
        return "" + this.getDetail();
    }

    @Override
    public int hashCode() {
        return this.getDetail().hashCode();
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
        return Objects.equals(this.getDetail(), other.getDetail());
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the detail
     */
    public OrderDetail getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(OrderDetail detail) {
        this.detail = detail;
    }

    /**
     * @return the type
     */
    public BuySellType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(BuySellType type) {
        this.type = type;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the succSymbol
     */
    public Symbol getSuccSymbol() {
        return succSymbol;
    }

    /**
     * @param succSymbol the succSymbol to set
     */
    public void setSuccSymbol(Symbol succSymbol) {
        this.succSymbol = succSymbol;
    }

    /**
     * @return the price
     */
    public Price getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * @param deal the deal to set
     */
    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    /**
     * @return the canDeal
     */
    public boolean isCanDeal() {
        return canDeal;
    }

    /**
     * @param canDeal the canDeal to set
     */
    public void setCanDeal(boolean canDeal) {
        this.canDeal = canDeal;
    }
}
