/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-27.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import me.jiaojie.ch.model.basic.OutputMessage;
import me.jiaojie.ch.model.basic.Message;
import me.jiaojie.ch.service.MyLogger;
//import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
@Controller
@RequestMapping("/")
public class WsController {

//    private final SimpMessagingTemplate template;

//    public WsController(SimpMessagingTemplate t) {
//        template = t;
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String viewApplication() {
        return "index";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public OutputMessage sendMessage(Message message) {
        MyLogger.debug(message);
//        template.send((org.springframework.messaging.Message<?>) new OutputMessage(message, new Date()));
        return new OutputMessage(message, new Date());
    }
}
