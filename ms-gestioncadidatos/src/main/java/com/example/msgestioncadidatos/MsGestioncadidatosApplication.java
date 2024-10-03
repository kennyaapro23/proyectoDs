package com.example.msgestioncadidatos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsGestioncadidatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestioncadidatosApplication.class, args);
	}

}
