package com.xsolla.estore;

import com.xsolla.estore.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EstoreApplication implements CommandLineRunner {

	@Autowired
	private ImportService importService;

	@Value("${importProducts:true}")
	private Boolean importProducts;

	public static void main(String[] args) {
		SpringApplication.run(EstoreApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (importProducts) {
			importService.importData();
		}
	}
}
