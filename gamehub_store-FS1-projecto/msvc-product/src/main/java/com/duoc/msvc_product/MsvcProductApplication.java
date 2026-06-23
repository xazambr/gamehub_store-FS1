package com.duoc.msvc_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcProductApplication.class, args);
	}

}
