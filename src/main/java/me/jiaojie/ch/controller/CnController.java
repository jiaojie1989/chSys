/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jiaojie.ch.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSON;
import java.util.Map;
import com.alibaba.fastjson.TypeReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.PriceJsonObj;
import me.jiaojie.ch.model.basic.OrderJsonObj;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.service.MyLogger;
import me.jiaojie.ch.service.Threads;
import me.jiaojie.ch.service.runner.OrderBuy;
import me.jiaojie.ch.service.runner.OrderSell;
import me.jiaojie.ch.service.runner.SetPrice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jiaojie
 */
@Controller
public class CnController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/cn/price", method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updatePrice() {
        Threads.Init();
        String output = "";
        InputStream is;
        try {
            is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            Map<String, PriceJsonObj> map = JSON.parseObject(contentStr, new TypeReference<Map<String, PriceJsonObj>>() {
            });
            map.forEach((String k, PriceJsonObj v) -> {
                Threads.getPriceHandler().execute(new SetPrice(v, new SymbolName(k)));
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

    @RequestMapping(value = "/cn/price/{symbolName}", method = {RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getPrice(@PathVariable String symbolName) {
        Symbol symbol = Cn.getInstance().getSymbol(new SymbolName(symbolName.toUpperCase()));
        String output = JSON.toJSONString(symbol);
        return output;
    }

    @RequestMapping(value = "/cn/order", method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String mkOrder() {
        Threads.Init();
        String output = "";
        InputStream is;
        try {
            is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            Map<String, OrderJsonObj> map = JSON.parseObject(contentStr, new TypeReference<Map<String, OrderJsonObj>>() {
            });
            map.forEach((String k, OrderJsonObj v) -> {
//                Threads.getPriceHandler().execute(new SetPrice(v, new SymbolName(k)));
                if ("buy".equals(v.getType())) {
                    Threads.getOrderHandler().execute(new OrderBuy(v, "cn"));
                } else if ("sell".equals(v.getType())) {
                    Threads.getOrderHandler().execute(new OrderSell(v, "cn"));
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

    @RequestMapping(value = "/cn/order/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public String getOrder() {
        return "ok";
    }

    @RequestMapping(value = "/cn/order", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelOrder() {
        return "ok";
    }
}
