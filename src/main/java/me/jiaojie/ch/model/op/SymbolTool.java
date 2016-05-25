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
package me.jiaojie.ch.model.op;

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.project.Trade;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class SymbolTool {

    public static Symbol getSymbolPrice(Trade project, SymbolName name) {
        return project.getSymbol(name);
    }

    public static void setSymbolPrice(Trade project, SymbolName name, Price ask, Price bid) {
        project.setSymbol(name, ask, bid);
    }
    
}
