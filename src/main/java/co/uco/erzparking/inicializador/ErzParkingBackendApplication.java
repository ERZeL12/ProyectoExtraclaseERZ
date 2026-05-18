package co.uco.erzparking.inicializador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"co.uco.erzparking"})
public class ErzParkingBackendApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ErzParkingBackendApplication.class, args);
	}

}
