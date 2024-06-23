package com.fiveeaglesti.clinicbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class ClinicBackendApplication {

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(ClinicBackendApplication.class, args);
	}

	@Bean("messages")
	public Properties messages() throws IOException {
		try {
			Properties properties = new Properties();
			Resource resource = resourceLoader.getResource("classpath:messages.properties");
			properties.load(resource.getInputStream());
			return properties;
		} catch (IOException e) {
			throw new IOException("Problemas ao carregar o arquivo classpath:messages.properties");
		}
	}

}
