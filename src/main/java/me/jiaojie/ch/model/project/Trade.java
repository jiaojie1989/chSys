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
import me.jiaojie.ch.service.MyLogger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
abstract public class Trade {

    protected Project project = null;
    protected ConcurrentHashMap<String, Symbol> priceMap = null;
    protected ConcurrentHashMap<String, TreeSet<Order>> sellOrderMap = null;
    protected ConcurrentHashMap<String, Boolean> sellOrderLock = null;
    protected ConcurrentHashMap<String, TreeSet<Order>> buyOrderMap = null;
    protected ConcurrentHashMap<String, Boolean> buyOrderLock = null;

    public ConcurrentHashMap<String, Symbol> getPriceMap() {
        return this.priceMap;
    }

    public Symbol getSymbol(SymbolName name) {
        if (priceMap.containsKey(name.toString())) {
            return priceMap.get(name.toString());
        } else {
            return SsetFactory.getEmptySymbol(this.project.getName(), name.getName());
        }
    }

    public Symbol getSymbol(String name) {
        if (priceMap.containsKey(name)) {
            return priceMap.get(name);
        } else {
            return SsetFactory.getEmptySymbol(this.project.getName(), name);
        }
    }

    public void setSymbol(SymbolName name, Price ask, Price bid) {
        this.priceMap.put(name.toString(), new Symbol(project, name, ask, bid));
    }

    public TreeSet<?> getSellOrderMap(String symbolName) {
        return sellOrderMap.get(symbolName);
    }

    public TreeSet<?> getBuyOrderMap(String symbolName) {
        return buyOrderMap.get(symbolName);
    }

    public Object getAllSellMap() {
        return sellOrderMap;
    }

    public Object getAllBuyMap() {
        return buyOrderMap;
    }

    abstract protected void init();

    abstract public int getListNum();

    abstract public Order getSuccOrder() throws InterruptedException;

    abstract public boolean addSuccOrder(Order order);

    public void setSymbolPrice(Symbol symbol) {
        this.priceMap.put(symbol.getSymbolName(), symbol);
    }

    public TreeSet<Order> getSuccSellTrade(Symbol symbol) {
        if (!this.sellOrderMap.containsKey(symbol.getSymbolName())) {
            this.sellOrderMap.put(symbol.getSymbolName(), SsetFactory.getEmptySet(new SellComparator()));
        }
        if (this.sellOrderMap.get(symbol.getSymbolName()).size() == 0) {
            return new TreeSet<Order>();
        } else {
            SortedSet<Order> temp;
            temp = this.sellOrderMap.get(symbol.getSymbolName()).tailSet(SsetFactory.getEmptyOrder(this.project, symbol, "sell"));
            if (temp.size() == 0) {
                return new TreeSet<Order>();
            } else {
                this.getSellLock(symbol.getSymbolName());
                TreeSet<Order> dealSet = new TreeSet<Order>(new SellComparator());
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

    public boolean mkSellOrder(Order order) {
        Symbol symbol = order.getSymbol();
        boolean status = false;
        this.getSellLock(symbol.getSymbolName());
        if (!this.sellOrderMap.containsKey(symbol.getSymbolName())) {
            this.sellOrderMap.put(symbol.getSymbolName(), SsetFactory.getEmptySet(new SellComparator()));
        }
        if (!this.sellOrderMap.get(symbol.getSymbolName()).contains(order)) {
            TreeSet<Order> temp = SsetFactory.getEmptySet(new BuyComparator());
            temp.addAll(this.sellOrderMap.get(symbol.getSymbolName()));
            temp.add(order);
            this.sellOrderMap.put(symbol.getSymbolName(), temp);
            status = true;
        }
        this.unlockSellLock(symbol.getSymbolName());
        return status;
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
            } else {
                TreeSet<Order> temp = SsetFactory.getEmptySet(new SellComparator());
                Iterator<Order> t = this.sellOrderMap.get(symbol.getSymbolName()).iterator();
                while (t.hasNext()) {
                    Order itOrder = t.next();
                    if (order.getDetail().getOrderId().equals(itOrder.getDetail().getOrderId())) {
                        status = true;
                    } else {
                        temp.add(itOrder);
                    }
                }
                this.sellOrderMap.put(symbol.getSymbolName(), temp);
            }
        }
        this.unlockSellLock(symbol.getSymbolName());
        return status;
    }

    protected void getSellLock(String name) {
        this.sellOrderLock.putIfAbsent(name, new Boolean(false));
        while (true) {
            if (this.sellOrderLock.replace(name, new Boolean(false), new Boolean(true))) {
                break;
            }
        }
    }

    protected void unlockSellLock(String name) {
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
            return new TreeSet<Order>();
        } else {
            SortedSet<Order> temp;
            try {
                temp = this.buyOrderMap.get(symbol.getSymbolName()).tailSet(SsetFactory.getEmptyOrder(this.project, symbol, "buy"));
            } catch (Exception e) {
                System.out.println(e);
                return new TreeSet<Order>();
            }
            if (temp.size() == 0) {
                return new TreeSet<Order>();
            } else {
                this.getBuyLock(symbol.getSymbolName());
                TreeSet<Order> dealSet = new TreeSet<Order>(new BuyComparator());
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

    public boolean mkBuyOrder(Order order) {
        Symbol symbol = order.getSymbol();
        boolean status = false;
        this.getBuyLock(symbol.getSymbolName());
        if (!this.buyOrderMap.containsKey(symbol.getSymbolName())) {
            this.buyOrderMap.put(symbol.getSymbolName(), SsetFactory.getEmptySet(new BuyComparator()));
        }
        if (!this.buyOrderMap.get(symbol.getSymbolName()).contains(order)) {
            TreeSet<Order> temp = SsetFactory.getEmptySet(new BuyComparator());
            temp.addAll(this.buyOrderMap.get(symbol.getSymbolName()));
            temp.add(order);
            this.buyOrderMap.put(symbol.getSymbolName(), temp);
            status = true;
        }
        this.unlockBuyLock(symbol.getSymbolName());
        return status;
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
            } else {
                TreeSet<Order> temp = SsetFactory.getEmptySet(new BuyComparator());
                Iterator<Order> t = this.sellOrderMap.get(symbol.getSymbolName()).iterator();
                while (t.hasNext()) {
                    Order itOrder = t.next();
                    if (order.getDetail().getOrderId().equals(itOrder.getDetail().getOrderId())) {
                        status = true;
                    } else {
                        temp.add(itOrder);
                    }
                }
                this.buyOrderMap.put(symbol.getSymbolName(), temp);
            }
        }
        this.unlockBuyLock(symbol.getSymbolName());
        return status;
    }

    protected void getBuyLock(String name) {
        this.buyOrderLock.putIfAbsent(name, new Boolean(false));
        while (true) {
            if (this.buyOrderLock.replace(name, new Boolean(false), new Boolean(true))) {
//                MyLogger.debug("Get Lock - " + name);
                break;
            }
//            MyLogger.debug("Try Get Lock Fail - " + name);
        }
    }

    protected void unlockBuyLock(String name) {
        while (true) {
            if (this.buyOrderLock.replace(name, new Boolean(true), new Boolean(false))) {
//                MyLogger.debug("Release Lock - " + name);
                break;
            }
        }
    }

}
