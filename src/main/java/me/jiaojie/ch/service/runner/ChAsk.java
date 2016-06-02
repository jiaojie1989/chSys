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
import me.jiaojie.ch.model.project.Hk;
import me.jiaojie.ch.model.project.Trade;
import me.jiaojie.ch.model.project.Us;
import me.jiaojie.ch.service.MyLogger;
import me.jiaojie.ch.service.Threads;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class ChAsk implements Runnable {

    private final Symbol symbol;
    private final Project project;
    private final String projectName;

    public ChAsk(Symbol symbol, String project) {
        this.symbol = symbol;
        this.projectName = project;
        this.project = ProjectFactory.getProject(project);
    }

    @Override
    public void run() {
        Trade cn;
        switch (projectName) {
            case "cn":
                cn = Cn.getInstance();
                break;
            case "us":
                cn = Us.getInstance();
                break;
            case "hk":
                cn = Hk.getInstance();
                break;
            default:
                cn = null;
                break;
        }

        if (null != cn) {
            TreeSet succSet = cn.getSuccBuyTrade(symbol);
//            MyLogger.debug("Succ Set:" + succSet);
            if (succSet.isEmpty()) {

            } else {
                Iterator<Order> I = succSet.iterator();
                while (I.hasNext()) {
                    Threads.getOrderSuccHandler().execute(new DealOrder(I.next(), projectName));
                }
            }
        } else {
            // 此处应有报警
        }
    }
}
