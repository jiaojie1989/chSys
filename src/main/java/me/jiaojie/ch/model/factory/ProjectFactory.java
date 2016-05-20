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
import me.jiaojie.ch.model.basic.*;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
public class ProjectFactory {

    protected static HashMap<String, Project> projectHashMap;

    public static Project getProject(String projectName) {
        if (projectHashMap.containsKey(projectName)) {
            
        } else {
            projectHashMap.put(projectName, new Project(projectName));
        }
        return projectHashMap.get(projectName);
    }
}
