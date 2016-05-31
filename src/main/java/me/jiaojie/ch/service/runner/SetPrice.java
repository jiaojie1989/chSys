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
import me.jiaojie.ch.model.project.Hk;
import me.jiaojie.ch.model.project.Trade;
import me.jiaojie.ch.model.project.Us;
import me.jiaojie.ch.service.Threads;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class SetPrice implements Runnable {

    private final PriceJsonObj priceObj;
    private final String projectName;
    private final Symbol symbol;
    private final SymbolName name;

    public SetPrice(PriceJsonObj priceObj, SymbolName name, String projectName) {
        this.priceObj = priceObj;
        this.name = name;
        this.projectName = projectName;
        this.symbol = new Symbol(ProjectFactory.getProject(this.projectName), this.name, new Price(priceObj.getAsk()), new Price(priceObj.getBid()));
    }

    @Override
    public void run() {
        Trade project;
        switch (projectName) {
            case "cn":
                project = Cn.getInstance();
                break;
            case "us":
                project = Us.getInstance();
                break;
            case "hk":
                project = Hk.getInstance();
                break;
            default:
                project = null;
                break;
        }

        if (null != project) {
            project.setSymbolPrice(this.symbol);
            Threads.getPriceScanner().execute(new ChAsk(symbol, projectName));
            Threads.getPriceScanner().execute(new ChBid(symbol, projectName));
        }
    }
}
