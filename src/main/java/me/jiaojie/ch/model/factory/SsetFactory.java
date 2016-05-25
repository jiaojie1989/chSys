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
package me.jiaojie.ch.model.factory;

import java.util.TreeSet;
import java.util.Comparator;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.basic.BuySellType;
import me.jiaojie.ch.model.basic.OrderDetail;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.factory.BuySellTypeFactory;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class SsetFactory {

    public static TreeSet<Order> getEmptySet(Comparator comparator) {
        return new TreeSet<Order>(comparator);
    }

    public static Order getEmptyOrder(Project project, Symbol symbol, String iType) {
        OrderDetail detail = null;
        BuySellType type = BuySellTypeFactory.getType(iType);
        Price price;
        if ("sell" == iType) {
            price = symbol.getAsk();
        } else {
            price = symbol.getBid();
        }
        return new Order(project, detail, type, symbol, price);
    }

    public static Order getEmptyOrder(Project project, Symbol symbol) {
        OrderDetail detail = null;
        BuySellType type = BuySellTypeFactory.getType("buy");
        Price price = symbol.getBid();
        return new Order(project, detail, type, symbol, price);
    }

    public static OrderDetail generateOrderDetail() {
        return new OrderDetail();
    }
    
    public static Symbol getEmptySymbol(String project, String name) {
        return new Symbol(ProjectFactory.getProject(project), new SymbolName(name), new Price(99999999999.9), new Price(-1.1));
    }
}
