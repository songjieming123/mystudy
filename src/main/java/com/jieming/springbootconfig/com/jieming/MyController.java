package com.jieming.springbootconfig.com.jieming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class MyController {

    @GetMapping("/test/test")
    public String test( ) {
        System.out.println("I like studying");
        return "knowledge let me happy";
    }

    @GetMapping("/test/test1")
    public void test1(int num) throws InterruptedException {
        log.info("{}接收到的请求请求序号,num = {}",Thread.currentThread().getName(),num);
        TimeUnit.HOURS.sleep(1);
    }


}
