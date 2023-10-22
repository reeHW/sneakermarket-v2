package com.sneakermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class MarketApplication {

	public static void main(String[] args) {;
		SpringApplication application = new SpringApplication(MarketApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
