package com.pichincha.demo.retriable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry //Anotación para habilitar Spring Retry en la aplicación
public class ProyectoPatronRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoPatronRetryApplication.class, args);
	}

}
