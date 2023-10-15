package com.api.gatca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//TODO ADD THIS FOR SECURITY https://github.com/buingoctruong/springboot3-springsecurity6-jwt/blob/master/src/main/java

@SpringBootApplication
@EnableScheduling
public class GatcaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatcaApplication.class, args);
	}

}
