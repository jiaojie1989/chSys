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
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class Symbol {

    private Project project;
    private Price bid; // ch buy price
    private Price ask; // ch sell price
    private SymbolName symbol;
    private long timestamp;

    public Symbol(Project project, SymbolName symbol, Price ask, Price bid, long timestmap) {
        this.project = project;
        this.symbol = symbol;
        this.ask = ask;
        this.bid = bid;
        this.timestamp = timestmap;
    }

    public Symbol(Project project, SymbolName symbol, Price ask, Price bid) {
        this.project = project;
        this.symbol = symbol;
        this.ask = ask;
        this.bid = bid;
        this.timestamp = System.currentTimeMillis() / 1000;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }

    public String getSymbolName() {
        return this.getSymbol().toString();
    }

    public Price getBidPrice() {
        return this.getBid();
    }

    public Price getAskPrice() {
        return this.getAsk();
    }

    public boolean canDeal(BuySellType queryType, Price queryPrice) {
        return true;
    }

    protected boolean canBuyOrderDeal(Price queryPrice) {
        return this.getBid().isNoMoreThan(queryPrice);
    }

    protected boolean canSellOrderDeal(Price queryPrice) {
        return this.getAsk().isNoLessThan(queryPrice);
    }

    public Price getDealPrice(BuySellType queryType) {
        if (queryType.isBuyType()) {
            return this.getBid();
        } else {
            return this.getAsk();
        }
    }

    @Override
    public String toString() {
        return "" + this.getProject() + "," + this.getSymbol();
    }

    @Override
    public int hashCode() {
        return DigestUtils.md5Hex("" + this.getProject() + this.getSymbol()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
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
     * @return the bid
     */
    public Price getBid() {
        return bid;
    }

    /**
     * @param bid the bid to set
     */
    public void setBid(Price bid) {
        this.bid = bid;
    }

    /**
     * @return the ask
     */
    public Price getAsk() {
        return ask;
    }

    /**
     * @param ask the ask to set
     */
    public void setAsk(Price ask) {
        this.ask = ask;
    }

    /**
     * @return the symbol
     */
    public SymbolName getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(SymbolName symbol) {
        this.symbol = symbol;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
