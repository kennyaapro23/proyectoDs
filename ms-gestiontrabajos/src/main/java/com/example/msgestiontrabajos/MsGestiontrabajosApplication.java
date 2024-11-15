package com.example.msgestiontrabajos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling // Habilita la programación de tareas
@SpringBootApplication
public class MsGestiontrabajosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestiontrabajosApplication.class, args);
	}
}
