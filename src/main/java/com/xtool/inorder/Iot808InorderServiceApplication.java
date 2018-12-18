package com.xtool.inorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Iot808InorderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Iot808InorderServiceApplication.class, args);
	}
}
