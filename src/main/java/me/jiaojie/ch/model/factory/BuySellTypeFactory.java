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
package me.jiaojie.ch.model.factory;

import java.util.HashMap;
import me.jiaojie.ch.model.basic.BuySellType;
import me.jiaojie.ch.service.MyLogger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class BuySellTypeFactory {

    protected static HashMap<String, BuySellType> typeHashMap = new HashMap<String, BuySellType>();

    public static BuySellType getType(String type) {
        if (type.equals("buy")) {
            type = "1";
        } else if (type.equals("sell")) {
            type = "-1";
        }
        MyLogger.info(type);
        if (typeHashMap.containsKey(type)) {

        } else {
            typeHashMap.put(type, new BuySellType(Integer.parseInt(type)));
        }
        return typeHashMap.get(type);
    }
}
