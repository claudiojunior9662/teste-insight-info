package br.com.teste.insightinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TesteInsightInfoApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TesteInsightInfoApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TesteInsightInfoApplication.class, args);
	}

}
