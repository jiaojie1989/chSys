/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-23.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.model.project;

import java.util.SortedSet;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.SymbolName;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TreeSet;
import java.util.Iterator;
import me.jiaojie.ch.model.factory.SsetFactory;
import me.jiaojie.ch.model.op.BuyComparator;
import me.jiaojie.ch.model.op.SellComparator;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
abstract public class Trade {

    protected Project project = null;
    protected ConcurrentHashMap<SymbolName, Symbol> priceMap = null;
    protected ConcurrentHashMap<SymbolName, TreeSet<Order>> sellOrderMap = null;
    protected ConcurrentHashMap<SymbolName, Boolean> sellOrderLock = null;
    protected ConcurrentHashMap<SymbolName, TreeSet<Order>> buyOrderMap = null;
    protected ConcurrentHashMap<SymbolName, Boolean> buyOrderLock = null;

    public Symbol getSymbol(SymbolName name) {
        if (priceMap.containsKey(name)) {
            return priceMap.get(name);
        } else {
            return null;
        }
    }
    
    public void setSymbol(SymbolName name, Price ask, Price bid) {
        this.priceMap.put(name, new Symbol(project, name, ask, bid));
    }

    abstract protected void init();

    public void setSymbolPrice(Symbol symbol) {
        this.priceMap.put(symbol.getSymbolName(), symbol);
    }

    public TreeSet<Order> getSuccSellTrade(Symbol symbol) {
        if (!this.sellOrderMap.containsKey(symbol.getSymbolName())) {
            this.sellOrderMap.put(symbol.getSymbolName(), SsetFactory.getEmptySet(new SellComparator()));
        }
        if (this.sellOrderMap.get(symbol.getSymbolName()).size() == 0) {
            return null;
        } else {
            SortedSet<Order> temp;
            temp = this.sellOrderMap.get(symbol.getSymbolName()).tailSet(SsetFactory.getEmptyOrder(this.project, symbol, "sell"));
            if (temp.size() == 0) {
                return null;
            } else {
                this.getSellLock(symbol.getSymbolName());
                TreeSet<Order> dealSet = new TreeSet<Order>();
                TreeSet<Order> origin = this.sellOrderMap.get(symbol.getSymbolName());
                TreeSet<Order> tempTreeSet = new TreeSet<Order>(temp);
                Iterator<Order> t = tempTreeSet.iterator();
                while (t.hasNext()) {
                    Order order = t.next();
                    origin.remove(order);
                    order.setSucc(symbol);
                    dealSet.add(order);
                }
                this.sellOrderMap.put(symbol.getSymbolName(), origin);
                this.unlockSellLock(symbol.getSymbolName());
                return dealSet;
            }
        }
    }

    public boolean cancelSellTrade(Order order) {
        Symbol symbol = order.getSymbol();
        boolean status = false;
        this.getSellLock(symbol.getSymbolName());
        if (this.sellOrderMap.get(symbol.getSymbolName()).size() == 0) {
        } else {
            if (this.sellOrderMap.get(symbol.getSymbolName()).contains(order)) {
                TreeSet<Order> temp = SsetFactory.getEmptySet(new SellComparator());
                temp.addAll(this.sellOrderMap.get(symbol.getSymbolName()));
                temp.remove(order);
                this.sellOrderMap.put(symbol.getSymbolName(), temp);
                status = true;
            }
        }
        this.unlockSellLock(symbol.getSymbolName());
        return status;
    }

    public void getSellLock(SymbolName name) {
        while (true) {
            if (this.sellOrderLock.putIfAbsent(name, new Boolean(false))) {
            }
            if (this.sellOrderLock.replace(name, new Boolean(false), new Boolean(true))) {
                break;
            }
        }
    }

    public void unlockSellLock(SymbolName name) {
        while (true) {
            if (this.sellOrderLock.replace(name, new Boolean(true), new Boolean(false))) {
                break;
            }
        }
    }

    public TreeSet<Order> getSuccBuyTrade(Symbol symbol) {
        if (!this.buyOrderMap.containsKey(symbol.getSymbolName())) {
            this.buyOrderMap.put(symbol.getSymbolName(), SsetFactory.getEmptySet(new BuyComparator()));
        }
        if (this.buyOrderMap.get(symbol.getSymbolName()).size() == 0) {
            return null;
        } else {
            SortedSet<Order> temp;
            temp = this.buyOrderMap.get(symbol.getSymbolName()).tailSet(SsetFactory.getEmptyOrder(this.project, symbol, "buy"));
            if (temp.size() == 0) {
                return null;
            } else {
                this.getBuyLock(symbol.getSymbolName());
                TreeSet<Order> dealSet = new TreeSet<Order>();
                TreeSet<Order> origin = this.buyOrderMap.get(symbol.getSymbolName());
                TreeSet<Order> tempTreeSet = new TreeSet<Order>(temp);
                Iterator<Order> t = tempTreeSet.iterator();
                while (t.hasNext()) {
                    Order order = t.next();
                    origin.remove(order);
                    order.setSucc(symbol);
                    dealSet.add(order);
                }
                this.buyOrderMap.put(symbol.getSymbolName(), origin);
                this.unlockBuyLock(symbol.getSymbolName());
                return dealSet;
            }
        }
    }

    public boolean cancelBuyTrade(Order order) {
        Symbol symbol = order.getSymbol();
        boolean status = false;
        this.getBuyLock(symbol.getSymbolName());
        if (this.buyOrderMap.get(symbol.getSymbolName()).size() == 0) {
        } else {
            if (this.buyOrderMap.get(symbol.getSymbolName()).contains(order)) {
                TreeSet<Order> temp = SsetFactory.getEmptySet(new BuyComparator());
                temp.addAll(this.buyOrderMap.get(symbol.getSymbolName()));
                temp.remove(order);
                this.buyOrderMap.put(symbol.getSymbolName(), temp);
                status = true;
            }
        }
        this.unlockBuyLock(symbol.getSymbolName());
        return status;
    }

    public void getBuyLock(SymbolName name) {
        while (true) {
            if (this.buyOrderLock.putIfAbsent(name, new Boolean(false))) {
            }
            if (this.buyOrderLock.replace(name, new Boolean(false), new Boolean(true))) {
                break;
            }
        }
    }

    public void unlockBuyLock(SymbolName name) {
        while (true) {
            if (this.buyOrderLock.replace(name, new Boolean(true), new Boolean(false))) {
                break;
            }
        }
    }

}
