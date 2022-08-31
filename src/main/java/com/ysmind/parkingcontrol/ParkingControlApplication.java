package com.ysmind.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingControlApplication {

	//Inicializa o app
	//TODO Aplicar filtros e parametros nas pesquisas.
	//TODO Estudar como o JPA  lida com tabelas e classes relacionadas.
	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);
	}
	@GetMapping("/")
	public String index(){
		return "Hello World!";
	}

}
