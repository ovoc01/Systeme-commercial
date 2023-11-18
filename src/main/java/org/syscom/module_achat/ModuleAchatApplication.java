package org.syscom.module_achat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.syscom.module_achat")
public class ModuleAchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleAchatApplication.class, args);
	}

}
