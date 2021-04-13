package com.chenhongliang.fortunetelling.controller;

import com.alibaba.fastjson.JSON;
import com.chenhongliang.fortunetelling.util.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping
public class IndexController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "玖言国学起名八字算命系统");
        return "index";
    }

    @PostMapping("/result")
    public String result(@RequestParam(value = "lastName") String lastName,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "sex") String sex,
                         @RequestParam(value = "date") String date,
                         @RequestParam(value = "hour") String hour,
                         @RequestParam(value = "minute") String minute,
                         @Nullable @RequestParam(value = "payed") String payed,
                         @Nullable @RequestParam(value = "service") Set<String> service,
                         Model model) throws IOException {
        Map pinyinMap = getPinyinMap();
        model.addAttribute("pinyinMap", pinyinMap);

        Map<String, String> formInfo = new LinkedHashMap<>();
        formInfo.put("姓", lastName);
        formInfo.put("名", name);
        formInfo.put("性别", sex);
        formInfo.put("出生日期", date);
        formInfo.put("出生 时", hour);
        formInfo.put("出生 分", minute);
        formInfo.put("是否付款", "payed".equals(payed) ? "是" : "否");

        Map<String, String> querys = new HashMap<>();
        querys.put("birthday", date);
        querys.put("hour", hour);
        querys.put("ming", name);
        querys.put("minute", minute);
        querys.put("pay", "payed".equals(payed) ? "1" : "0");
        querys.put("sex", sex);
        querys.put("xing", lastName);

        model.addAttribute("formInfo", formInfo);
        model.addAttribute("title", "玖言国学起名八字算命系统");

        if (service != null) {
            if (service.contains("mingpen")) {
                //八字命盘
                model.addAttribute("mingpen", getMapFromAPI("/openapi/bazi/getMingpen", querys));
            }
            if (service.contains("mingju")) {
                //命局分析
                model.addAttribute("mingju", getMapFromAPI("/openapi/bazi/getMingju", querys));
            }
        }
        return "result";
    }

    public static Map getPinyinMap() throws IOException {
        File jsonFile = ResourceUtils.getFile("classpath:static/pinyinmap.json");
        String jsonStr = FileUtils.readFileToString(jsonFile);
        Map jsonMap=JSON.parseObject(String.valueOf(jsonStr), HashMap.class);

        return jsonMap;
    }

    private Map getMapFromAPI(String path, Map<String, String> querys){
        String host = "https://openapi.fatebox.cn";
        String method = "GET";
        String appcode = "32cf3b4f21904b27bd7877354307b724";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            Map mapType = JSON.parseObject(result, LinkedHashMap.class);
            return mapType;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

}
