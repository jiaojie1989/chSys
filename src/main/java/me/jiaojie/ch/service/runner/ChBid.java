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

import java.util.TreeSet;
import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.Project;
import me.jiaojie.ch.model.factory.ProjectFactory;
import me.jiaojie.ch.model.project.Cn;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class ChBid implements Runnable {

    protected Symbol symbol;
    protected Project project;
    protected Runnable getProject;

    public ChBid(Symbol symbol, Project project) {
        this.symbol = symbol;
        this.project = project;
    }

    public ChBid(Symbol symbol, String project) {
        this.symbol = symbol;
        this.project = ProjectFactory.getProject(project);
    }

    @Override
    public void run() {
        Cn cn = Cn.getInstance();
        TreeSet succSet = cn.getSuccSellTrade(symbol);
    }
}
