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

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.Project;

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

    @Override
    public void run() {
    }
}