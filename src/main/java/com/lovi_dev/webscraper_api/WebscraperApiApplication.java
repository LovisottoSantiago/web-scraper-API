package com.lovi_dev.webscraper_api;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovi_dev.service.Product;
import com.lovi_dev.service.ScrapingServiceExample;
import com.lovi_dev.service.elIndio;




@RestController
@SpringBootApplication
public class WebscraperApiApplication {
	private final ScrapingServiceExample scrapingService = new ScrapingServiceExample();
	private final elIndio indioPrueba = new elIndio();

	public static void main(String[] args) {
		SpringApplication.run(WebscraperApiApplication.class, args);
	}

	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

	@GetMapping("/productos")
	public List<Product> getProducts(){
		return scrapingService.getProducts();
	}

	
	@GetMapping("/indio")
	public List<Product> indio(){
		return indioPrueba.getIndio();
	}
	

}
