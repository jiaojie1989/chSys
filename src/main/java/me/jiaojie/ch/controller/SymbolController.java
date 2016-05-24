/*
 * Copyright (C) 2016 SINA Corporation
 *  
 *  
 * 
 * This script is firstly created at 2016-05-19.
 * 
 * To see more infomation,
 *    visit our official website http://jiaoyi.sina.com.cn/.
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

import me.jiaojie.ch.model.basic.Symbol;
import me.jiaojie.ch.model.basic.PriceJsonObj;
import me.jiaojie.ch.model.basic.SymbolName;
import me.jiaojie.ch.model.project.Cn;
import me.jiaojie.ch.service.Threads;
import me.jiaojie.ch.service.runner.SetPrice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
//@Controller
@RestController
public class SymbolController {

    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private CarService carService;
//    @RequestMapping("/car/list")
//    public void carList(Model model) {
//        List<Car> carList = carService.findAll();
//        model.addAttribute("carList", carList);
//    }
//
//    @RequestMapping("/car/add")
//    public void carAdd() {
//    }
//
//    @RequestMapping(value = "/car/add", method = RequestMethod.POST)
//    public String carAddSubmit(@ModelAttribute("car") @Valid Car car, BindingResult result) {
//        if (result.hasErrors()) {
//            // show the form again, with the errors
//            return "car/add";
//        }
//
//        // validation was successful
//        carService.add(car);
//        return "redirect:/car/list";
//    }
    @RequestMapping(value = {"/", "/welcome", "/index"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain")
    @ResponseBody
    public String welcome() {
        return "Hello, World!";
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll() {
//        ConcurrentHashMap<String, Symbol> map = Cn.getInstance().getPriceMap();
//        System.out.println(map);
        Symbol symbol = Cn.getInstance().getSymbol(new SymbolName("BABA"));
        System.out.println(symbol.getTimestamp());
        String output = JSON.toJSONString(symbol);
        return output;
    }

    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.PUT})
    public void test() {
        InputStream is;
        try {
            is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            System.out.println(contentStr);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public String testJson() {
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
            output = "ok";
        } catch (IOException e) {
            System.out.println(e);
            output = "io error";
        } catch (Exception e) {
            System.out.println(e);
            output = "exception";
        } finally {
            return output;
        }
    }

}
