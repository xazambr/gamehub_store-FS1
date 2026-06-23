package com.duoc.msvc_inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcInventoryApplication.class, args);
	}

}
