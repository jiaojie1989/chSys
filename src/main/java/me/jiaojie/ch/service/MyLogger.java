/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-25.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class MyLogger {

    private static Logger logger = LogManager.getLogger("trade");

    public static void info(Object obj) {
        logger.info(obj);
    }

    public static void debug(Object obj) {
        logger.debug(obj);
    }

    public static void warn(Object obj) {
        logger.warn(obj);
    }

    public static void error(Object obj) {
        logger.error(obj);
    }

    public static void fatal(Object obj) {
        logger.fatal(obj);
    }
}
