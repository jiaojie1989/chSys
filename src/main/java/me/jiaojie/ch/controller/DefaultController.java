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
import me.jiaojie.ch.service.Mailer;
import me.jiaojie.ch.service.Threads;
import me.jiaojie.ch.service.runner.SetPrice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.pegdown.PegDownProcessor;

/**
 * 默认接口
 *
 * @author jiaojie <jiaojie@staff.sina.com>
 */
//@Controller
@RestController
@RequestMapping("/")
public class DefaultController {

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
    @RequestMapping(value = {"/", "/welcome", "/index", "/index.php", "/index.jsp", "/index.do", "/index.html"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html")
    @ResponseBody
    public String welcome() {
        Threads.Init();
        PegDownProcessor mdProcessor = new PegDownProcessor();
        String md = "Price Compare System\n"
                + "===\n"
                + "# Apis\n"
                + "## Base Url\n"
                + "http://172.16.7.27:8080/test\n"
                + "\n"
                + "## Projects\n"
                + "* cn\n"
                + "* hk\n"
                + "* us\n"
                + "\n"
                + "## Update price\n"
                + "* url\n"
                + "http://172.16.7.27:8080/test/{projectName}/price\n"
                + "\n"
                + "* http method\n"
                + "`PUT` or `POST`\n"
                + "\n"
                + "* json data\n"
                + "```\n"
                + "{\n"
                + "    \"Symbol1\": {\n"
                + "        \"ask\": \"19,900\",\n"
                + "        \"bid\": \"19.900\",\n"
                + "        \"timestamp\": 1464864251 // [opt]\n"
                + "    },\n"
                + "    \"Symbol2\": {\n"
                + "        \"ask\": \"19,900\",\n"
                + "        \"bid\": \"19.900\",\n"
                + "        \"timestamp\": 1464864251 // [opt]\n"
                + "    },\n"
                + "    \"Symbol3\": {\n"
                + "        \"ask\": \"19,900\",\n"
                + "        \"bid\": \"19.900\",\n"
                + "        \"timestamp\": 1464864251 // [opt]\n"
                + "    }\n"
                + "}\n"
                + "```\n"
                + "\n"
                + "If timestamp is not sended in http request, the default timestamp is system timestamp.\n"
                + "\n"
                + "* return\n"
                + "`ok` is ok, other json format string is error.\n"
                + "\n"
                + "## Add order\n"
                + "* url\n"
                + "http://172.16.7.27:8080/test/{projectName}/order\n"
                + "\n"
                + "* http method\n"
                + "`PUT` or `POST`\n"
                + "\n"
                + "* json data\n"
                + "```\n"
                + "{\n"
                + "    \"orderId\": {\n"
                + "        \"orderId\": \"orderId\",\n"
                + "        \"symbol\": \"symbol\",\n"
                + "        \"wait\": \"0\", // 0 for now price, 1 wait for next price\n"
                + "        \"sid\": \"1\", // user sid\n"
                + "        \"type\": \"buy\", // buy or sell\n"
                + "        \"amount\": \"1\", // order amount\n"
                + "        \"price\": \"20.000\", // order price\n"
                + "        \"timestamp\": 1464864251 // [opt]\n"
                + "    }\n"
                + "}\n"
                + "```\n"
                + "\n"
                + "If timestamp is not sended in http request, the default timestamp is system timestamp.\n"
                + "\n"
                + "This structure could contain more than one order detail.\n"
                + "\n"
                + "* return\n"
                + "`ok` is ok, other json format string is error.\n"
                + "\n"
                + "## Data receive\n"
                + "* url\n"
                + "http://172.16.7.27:8080/test/{projectName}/result\n"
                + "\n"
                + "* procotol\n"
                + "websocket\n"
                + "\n"
                + "* json data\n"
                + "```\n"
                + "{\n"
                + "    \"canDeal\":false,\n"
                + "    \"deal\":true,\n"
                + "    \"detail\":{\n"
                + "        \"amount\":879,\n"
                + "        \"orderId\":\"0b420964ea7c7a5ed037744cd22a5bef\",\n"
                + "        \"sid\":\"9499786\",\n"
                + "        \"timestamp\":1464863505\n"
                + "    },\n"
                + "    \"orderPrice\":{\n"
                + "        \"price\":26.82\n"
                + "    },\n"
                + "    \"price\":{\n"
                + "        \"$ref\":\"$.orderPrice\"\n"
                + "    },\n"
                + "    \"project\":{\n"
                + "        \"name\":\"us\"\n"
                + "    },\n"
                + "    \"succSymbol\":{\n"
                + "        \"ask\":{\n"
                + "            \"price\":14.82\n"
                + "        },\n"
                + "        \"bid\":{\n"
                + "            \"price\":14.8\n"
                + "        },\n"
                + "        \"project\":{\n"
                + "            \"$ref\":\"$.project\"\n"
                + "        },\n"
                + "        \"symbol\":{\n"
                + "            \"name\":\"WB\"\n"
                + "        },\n"
                + "        \"symbolName\":\"WB\",\n"
                + "        \"timestamp\":1464863506\n"
                + "    },\n"
                + "    \"symbol\":{\n"
                + "        \"ask\":{\n"
                + "            \"price\":13.15\n"
                + "        },\n"
                + "        \"bid\":{\n"
                + "            \"price\":13.13\n"
                + "        },\n"
                + "        \"project\":{\n"
                + "            \"$ref\":\"$.project\"\n"
                + "        },\n"
                + "        \"symbol\":{\n"
                + "            \"name\":\"WB\"\n"
                + "        },\n"
                + "        \"symbolName\":\"WB\",\n"
                + "        \"timestamp\":1464863504\n"
                + "    },\n"
                + "    \"type\":{\n"
                + "        \"buyType\":false,\n"
                + "        \"sellType\":true,\n"
                + "        \"type\":-1\n"
                + "    }\n"
                + "}\n"
                + "```\n"
                + "\n"
                + "One piece.";
        return mdProcessor.markdownToHtml(md);
    }

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public String getAll() {
////        ConcurrentHashMap<String, Symbol> map = Cn.getInstance().getPriceMap();
////        System.out.println(map);
//        Symbol symbol = Cn.getInstance().getSymbol(new SymbolName("BABA"));
//        System.out.println(symbol.getTimestamp());
//        String output = JSON.toJSONString(symbol);
//        return output;
//    }
//
//    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.PUT})
//    public void test() {
//        InputStream is;
//        try {
//            is = request.getInputStream();
//            String contentStr = IOUtils.toString(is, "utf-8");
//            System.out.println(contentStr);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//
//    @RequestMapping(value = "/json", method = RequestMethod.POST)
//    public String testJson() {
//        Threads.Init();
//        String output = "";
//        InputStream is;
//        try {
//            is = request.getInputStream();
//            String contentStr = IOUtils.toString(is, "utf-8");
//            Map<String, PriceJsonObj> map = JSON.parseObject(contentStr, new TypeReference<Map<String, PriceJsonObj>>() {
//            });
//            map.forEach((String k, PriceJsonObj v) -> {
//                Threads.getPriceHandler().execute(new SetPrice(v, new SymbolName(k)));
//            });
//            output = "ok";
//        } catch (IOException e) {
//            System.out.println(e);
//            output = "io error";
//        } catch (Exception e) {
//            System.out.println(e);
//            output = "exception";
//        } finally {
//            return output;
//        }
//    }
}
