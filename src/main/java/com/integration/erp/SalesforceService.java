package com.integration.erp;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service(value="salesforceService")
public class SalesforceService {

    private final RestTemplate restTemplate;
    private final SalesforceConfig salesforceConfig;

    public SalesforceService(RestTemplateBuilder restTemplateBuilder, SalesforceConfig salesforceConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.salesforceConfig = salesforceConfig;
    }

    public String getAccessToken() {

        System.out.println("salesforceConfig.getTokenUrl "+ salesforceConfig.getTokenUrl());
        System.out.println("salesforceConfig.getClientId "+ salesforceConfig.getClientId());
        System.out.println("salesforceConfig.getClientSecret "+ salesforceConfig.getClientSecret());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", salesforceConfig.getClientId());
        body.add("client_secret", salesforceConfig.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                salesforceConfig.getTokenUrl(),
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {});

        return response.getBody().get("access_token");
    }

    public Map<String, Object> getAccountById(String accountId) {
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                salesforceConfig.getAccountEndpoint() + "/" + accountId,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
