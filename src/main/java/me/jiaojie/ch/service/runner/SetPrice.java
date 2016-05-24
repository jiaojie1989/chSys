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
package me.jiaojie.ch.service.runner;

import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.basic.PriceJsonObj;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.factory.ProjectFactory;
import me.jiaojie.ch.model.project.Cn;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class SetPrice implements Runnable {

    protected PriceJsonObj priceObj;
    protected Symbol symbol;
    protected SymbolName name;

    public SetPrice(PriceJsonObj priceObj, SymbolName name) {
        this.priceObj = priceObj;
        this.name = name;
        this.symbol = new Symbol(ProjectFactory.getProject("cn"), this.name, new Price(priceObj.ask), new Price(priceObj.bid));
    }

    @Override
    public void run() {
        Cn cn = Cn.getInstance();
        cn.setSymbolPrice(this.symbol);
    }
}