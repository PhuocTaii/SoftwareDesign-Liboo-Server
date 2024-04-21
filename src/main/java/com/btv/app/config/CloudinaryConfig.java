package com.btv.app.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
    Dotenv dotenv = Dotenv.load();
    final String cloud_name = dotenv.get("cloud_name");
    final String api_key = dotenv.get("api_key");
    final String api_secret = dotenv.get("api_secret");

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        return new Cloudinary(config);
    }
}
