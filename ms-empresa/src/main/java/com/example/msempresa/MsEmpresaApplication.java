package com.example.msempresa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEmpresaApplication.class, args);
	}

}
