package com.jieyun.common.resoreces.trading.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PublicResorecesTradingEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicResorecesTradingEsApplication.class, args);
    }

}
