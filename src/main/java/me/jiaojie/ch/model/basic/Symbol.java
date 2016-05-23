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

    protected Project project;
    protected Price bid; // ch buy price
    protected Price ask; // ch sell price
    protected SymbolName symbol;
    // add timestamp

    public Symbol(Project project, SymbolName symbol, Price ask, Price bid) {
        this.project = project;
        this.symbol = symbol;
        this.ask = ask;
        this.bid = bid;
    }

    public SymbolName getSymbolName() {
        return this.symbol;
    }

    public Price getBidPrice() {
        return this.bid;
    }

    public Price getAskPrice() {
        return this.ask;
    }

    public boolean canDeal(BuySellType queryType, Price queryPrice) {
        return true;
    }

    protected boolean canBuyOrderDeal(Price queryPrice) {
        return this.bid.isNoMoreThan(queryPrice);
    }

    protected boolean canSellOrderDeal(Price queryPrice) {
        return this.ask.isNoLessThan(queryPrice);
    }

    public Price getDealPrice(BuySellType queryType) {
        if (queryType.isBuyType()) {
            return this.bid;
        } else {
            return this.ask;
        }
    }

    @Override
    public String toString() {
        return "" + this.project + "," + this.symbol;
    }

    @Override
    public int hashCode() {
        return DigestUtils.md5Hex("" + this.project + this.symbol).hashCode();
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
}
