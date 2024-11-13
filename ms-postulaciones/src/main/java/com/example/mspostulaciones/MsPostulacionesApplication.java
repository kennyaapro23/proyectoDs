package com.example.mspostulaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsPostulacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPostulacionesApplication.class, args);
	}

}
