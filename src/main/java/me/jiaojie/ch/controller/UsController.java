/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-31.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
 */
package me.jiaojie.ch.controller;

import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Cleanup;

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.PriceJsonObj;
import me.jiaojie.ch.model.basic.OrderJsonObj;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.service.Threads;
import me.jiaojie.ch.service.runner.OrderBuy;
import me.jiaojie.ch.service.runner.OrderSell;
import me.jiaojie.ch.service.runner.SetPrice;
import me.jiaojie.ch.model.basic.Order;
import me.jiaojie.ch.model.basic.OrderDetail;
import me.jiaojie.ch.model.basic.Price;
import me.jiaojie.ch.model.factory.BuySellTypeFactory;
import me.jiaojie.ch.model.factory.ProjectFactory;
import me.jiaojie.ch.model.project.Us;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
@Controller
@RequestMapping("/us")
public class UsController {

    /**
     * Http Request Body
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 更新价格
     *
     * @return String
     */
    @RequestMapping(value = "/price", method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updatePrice() {
        Threads.Init();
        String output = "";
        try {
            @Cleanup
            InputStream is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            Map<String, PriceJsonObj> map = JSON.parseObject(contentStr, new TypeReference<Map<String, PriceJsonObj>>() {
            });
            map.forEach((String k, PriceJsonObj v) -> {
                Threads.getPriceHandler().execute(new SetPrice(v, new SymbolName(k), "us"));
            });
            output = JSON.toJSONString("ok");
        } catch (IOException e) {
            System.out.println(e);
            output = JSON.toJSONString(e);
        } catch (Exception e) {
            System.out.println(e);
            output = JSON.toJSONString(e);
        } finally {
            return output;
        }
    }

    /**
     * 获取某只股票的当前价格Json
     *
     * @param symbolName
     * @return String
     */
    @RequestMapping(value = "/price/{symbolName}", method = {RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getPrice(@PathVariable String symbolName) {
        Symbol symbol = Us.getInstance().getSymbol(new SymbolName(symbolName.toUpperCase()));
        String output = JSON.toJSONString(symbol);
        return output;
    }

    /**
     * 下单接口
     *
     * @return String
     */
    @RequestMapping(value = "/order", method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String mkOrder() {
        Threads.Init();
        String output = "";
        try {
            @Cleanup
            InputStream is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            Map<String, OrderJsonObj> map = JSON.parseObject(contentStr, new TypeReference<Map<String, OrderJsonObj>>() {
            });
            map.forEach((String k, OrderJsonObj v) -> {
                if ("buy".equals(v.getType())) {
                    Threads.getOrderHandler().execute(new OrderBuy(v, "us"));
                } else if ("sell".equals(v.getType())) {
                    Threads.getOrderHandler().execute(new OrderSell(v, "us"));
                }
            });
            output = JSON.toJSONString("ok");
        } catch (IOException e) {
            output = JSON.toJSONString(e);
        } catch (Exception e) {
            output = JSON.toJSONString(e);
        } finally {
            return output;
        }
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public String getOrder() {
        return "ok";
    }

    /**
     * 取消订单接口
     *
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelOrder() {
        Threads.Init();
        String output = "";
        try {
            @Cleanup
            InputStream is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            OrderJsonObj orderObj = JSON.parseObject(contentStr, OrderJsonObj.class);
            boolean ret = false;
            Symbol symbol = Us.getInstance().getSymbol(orderObj.getSymbol());
            if ("buy".equals(orderObj.getType())) {
                Order order = new Order(ProjectFactory.getProject("us"), new OrderDetail(orderObj.getOrderId(), orderObj.getSid(), orderObj.getAmount(), orderObj.getTimestamp()), BuySellTypeFactory.getType("buy"), symbol, new Price(orderObj.getPrice()), orderObj.getTimestamp());
                ret = Us.getInstance().cancelBuyTrade(order);
            } else if ("sell".equals(orderObj.getType())) {
                Order order = new Order(ProjectFactory.getProject("us"), new OrderDetail(orderObj.getOrderId(), orderObj.getSid(), orderObj.getAmount(), orderObj.getTimestamp()), BuySellTypeFactory.getType("sell"), symbol, new Price(orderObj.getPrice()), orderObj.getTimestamp());
                ret = Us.getInstance().cancelSellTrade(order);
            }
            output = JSON.toJSONString(ret);
        } catch (IOException e) {
            output = JSON.toJSONString(e);
        } catch (Exception e) {
            output = JSON.toJSONString(e);
        } finally {
            return output;
        }
    }
}
