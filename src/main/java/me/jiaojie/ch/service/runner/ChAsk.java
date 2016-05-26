/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-20.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service.runner;

import java.util.Iterator;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.factory.ProjectFactory;
import me.jiaojie.ch.model.project.Cn;
import java.util.TreeSet;
import me.jiaojie.ch.service.Threads;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class ChAsk implements Runnable {

    protected Symbol symbol;
    protected Project project;
    protected Runnable getProject;

    public ChAsk(Symbol symbol, Project project) {
        this.symbol = symbol;
        this.project = project;
    }

    public ChAsk(Symbol symbol, String project) {
        this.symbol = symbol;
        this.project = ProjectFactory.getProject(project);
    }

    @Override
    public void run() {
        Cn cn = Cn.getInstance();
        TreeSet succSet = cn.getSuccBuyTrade(symbol);
        if (succSet.isEmpty()) {

        } else {
            Iterator<Order> I = succSet.iterator();
            while (I.hasNext()) {
                Threads.getOrderSuccHandler().execute(new DealOrder(I.next()));
            }
        }
    }
}
