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
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

import me.jiaojie.ch.model.basic.Symbol;

/**
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
@Controller
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
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void test() {
        InputStream is = null;
        try {
            is = request.getInputStream();
            String contentStr = IOUtils.toString(is, "utf-8");
            System.out.println(contentStr);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
