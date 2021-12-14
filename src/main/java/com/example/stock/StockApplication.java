package com.example.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SpringBootApplication
@RestController
public class StockApplication {
	private final StockCalculationService service;

	public StockApplication(StockCalculationService service) {
		this.service = service;
	}

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

	@GetMapping("/")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@RequestMapping("/stock")
	public @ResponseBody
	List<Order> calculateStock(){
		int stock = 300;
		List<Order> orderLine = service.getOrders();
		return service.allocateStocksForOrderLine(orderLine,stock);
	}
}