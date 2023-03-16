package com.app.MobileAppProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileAppProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(MobileAppProjectApplication.class, args);
		String currentDir = System.getProperty("user.dir");
		System.out.println("Current dir using System:" + currentDir);

	}

}
