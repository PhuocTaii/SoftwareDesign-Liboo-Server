package com.btv.app;

import com.btv.app.config.FirebaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		FirebaseConfig firebaseConfig = new FirebaseConfig();
		firebaseConfig.initialized();
		SpringApplication.run(AppApplication.class, args);
	}

}
