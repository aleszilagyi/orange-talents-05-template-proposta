package com.orangetalents.biometria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableFeignClients
@EnableSpringDataWebSupport
public class BiometriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometriaApplication.class, args);
    }

}
