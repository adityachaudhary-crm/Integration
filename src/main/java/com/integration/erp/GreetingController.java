package com.integration.erp;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

    GreetingController(SalesforceService salesforceService) {
        this.salesforceService = salesforceService;
    }

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	private final SalesforceService salesforceService;

	@GetMapping("/account/{id}")
    public ResponseEntity<Map<String, Object>> getAccountById(@PathVariable String id) {
        Map<String, Object> account = salesforceService.getAccountById(id);
        return ResponseEntity.ok(account);
    }
}