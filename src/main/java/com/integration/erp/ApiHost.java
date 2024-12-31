package com.integration.erp;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiHost {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	private final SalesforceService salesforceService;

    ApiHost(SalesforceService salesforceService) {
        this.salesforceService = salesforceService;
    }

	@GetMapping("/heartbeat")
	public HeartBeat heartBeat(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new HeartBeat(counter.incrementAndGet(), String.format(template, name));
	}
	

	@GetMapping("/account/{id}")
    public ResponseEntity<Map<String, Object>> getAccountById(@PathVariable String id) {
        Map<String, Object> account = salesforceService.getAccountById(id);
        return ResponseEntity.ok(account);
    }
	@GetMapping("/jwt/account/{id}")
    public String getAccountByIdJWT(@PathVariable String id) {
        String account = new SalesforceServiceJWT().getAccountDetails(id);
        return account;
    }
}