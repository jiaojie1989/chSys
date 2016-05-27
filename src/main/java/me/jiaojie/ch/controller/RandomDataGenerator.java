///*
// * Copyright (C) 2016 SINA Corporation
// *  
// *  
// * 
// * This script is firstly created at 2016-05-27.
// * 
// * To see more infomation,
// *    visit our official website http://jiaoyi.sina.com.cn/.
// */
//package me.jiaojie.ch.controller;
//
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.core.MessageSendingOperations;
//import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author jiaojie <jiaojie@staff.sina.com>
// */
//@Component
//public class RandomDataGenerator implements ApplicationListener<BrokerAvailabilityEvent> {
//
//    private final MessageSendingOperations<String> messagingTemplate;
//
//    @Autowired
//    public RandomDataGenerator(
//            final MessageSendingOperations<String> messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @Override
//    public void onApplicationEvent(final BrokerAvailabilityEvent event) {
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void sendDataUpdates() {
//        System.out.println("out");
//        this.messagingTemplate.convertAndSend(
//                "/topic/message", new Random().nextInt(100));
//
//    }
//}