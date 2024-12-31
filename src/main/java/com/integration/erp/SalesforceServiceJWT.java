package com.integration.erp;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
@Service(value="salesforceServiceJWT")
public class SalesforceServiceJWT {
    
    private static final String SALESFORCE_TOKEN_URL = "https://adityachaudharycrm-dev-ed.develop.my.salesforce.com/services/oauth2/token";
    private static final String SALESFORCE_BASE_URL = "https://adityachaudharycrm-dev-ed.develop.my.salesforce.com";
    private static final String CLIENT_ID = "3MVG9XgkMlifdwVBcasuMyH_Jjb27TCC7qDtcA36AN9hkhK_0tS8eHs9sN97QjCD3HfADop5QB1lJkUZErXKg";
    private static final String USERNAME = "consultant@adityachaudharycrm.com";
    private static final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDOifYAxHsle7EH+Z4zIj5HAeDlxApS4YysUkZhxWieTLCwJ3DoSalKJybh5VgAEMS4aw68pdHSm64bdILL9Z1Yta7oJ+bsvEnmqRdyiouPP1ixNf+Njv3AQeB2HNu3QnTy+0Yt6K/Xhd+Pk5fIaEk5dIJwGpk8zb9MIRYn8BTer3Tnm66ECcVhCoE7KGOixp5gGVlCQoKJmpDEnkciIDwE0BLgThM/5RSlcfZLCZqNUR4dXp9RvUfMwXeQGilvok6LHP17j3bJawQdB5SjwAAi0fhJej16MnLvrE6dRXpjuOCCtbX7GW7kwFPao5M7vSxDn6DCqVnRComqJXt/HH95AgMBAAECggEAJ1IyqJC/lTJIUOlgCgIh8yjTZUr+YCsaug/r4F9R/JlDJjZME506gqiTiXw9Vb3bhfSz2IyFNuB2tDlpUWVQcHwecYpvN3DdbPGSSjdAPKRlwU8zeQLZIoooLrRtzCT12PCnusPK/nA5UL9brUbVcf/ReYBG3kGZguflrPLTNlW/cDeOIjTwbvzL6IM8ENTEZ/wgSlUYrZ2OIwVAnxzp+fB9YD3kWyZ1+Qhd767wL9SJZkSMqfzqaMUWXuztVAcFrM0vTCSR8bKD7Co0yoZt0Ah1HTIhPnplLPIBl+yZV/VHGbgKXImThzZNWcdLTpQzhBOZR3DzbriadoKsF78igQKBgQDyszXoSMS4uF9frr7J4Pi/9Z1y36WZTYGIf8f/M30/sWaoZpu2wHeva0VXoHTYcbJ1lZsx8D8UDjSIr5Bryro5nBoN9Fmm9gxhPFHk1AaGkyN6f3gepljEYgEXNo2kiMyM4xQ+xJpfCSSSnmoRKq+uukFz2kVbL9nXMfooFywCqQKBgQDZ23QG6IpoJiZ8ZQ54KwsMQfiUp1Gm2GBwbPt3bFvERjrViuKWFxkq4pj5hJW0aCLZeBwA+XoCI4yaGPSskwdGXVFaO/ltITmhSLDoMyDl3XA3ix3KMymligNO/4KGVmLghN9oORp2SWxD5AFuacgTrgfe1j+JhWNPh80ODCNoUQKBgHN9V/+q8QG9qpTLgLpsNbKS3FOXnSOwQB2GQNNt2wH+Fw23lg2G1O1CrIKgNjnxEKznfixrWHjgsZsBgEXq+GcFo1hUmHzcxNNYctfjFR/g0JcwJSbgnYkF4eL78LcE6eF5BV9BCvyCS0cB3xNALs2EkDTi/vH/eTfv7kw5ipXRAoGAapSAOPj+7WDspyLRZ1y3dPG5aRBKU/O7ioTmqVArvOQW2G2v2HnIaECfUkLY8n+XvQiTuq5SUEWP/buDyAHOJ1B0Ak8hAZClnw/u7EpHJ+2jfqjilA9fqorQf49lDc+pY+ndWiHFnmThQH2Fbbz2kxoq44uxXdgR89CeMuchU2ECgYAE7peVTBS8drg1IjGmdqrBXOgspnY3YN1BjHhUxuPdqlEdu4CG6EF3xvX3LKwShvXEIJG4vw5F4KSzbGkH5TBHC4HQaG666eZqdosNxMaX9OwgXcUXNRuQvY4D2gMj9Wtq7vzhd7BhFhnHcXSpDMneaeoZoK1yByPtdjYnJ/IAqA==-----END PRIVATE KEY-----";

    public String getAccountDetails(String accountId) {
        try {
            String jwtToken = generateJwtToken();
            String accessToken = getAccessToken(jwtToken);
            return fetchAccountDetails(accessToken, accountId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching account details", e);
        }
    }

    private String generateJwtToken() throws Exception {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + 300000); // 5 minutes expiration

        PrivateKey privateKey = getPrivateKeyFromPem(PRIVATE_KEY);

        return Jwts.builder()
                .setIssuer(CLIENT_ID)
                .setSubject(USERNAME)
                .setAudience("https://login.salesforce.com")
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    private PrivateKey getPrivateKeyFromPem(String privateKeyPem) throws Exception {
        String privateKeyCleaned = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "")
                                               .replace("-----END PRIVATE KEY-----", "")
                                               .replaceAll("\\s", "");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyCleaned);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        return keyFactory.generatePrivate(keySpec);
    }

    private String getAccessToken(String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=" + jwtToken;

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(SALESFORCE_TOKEN_URL, HttpMethod.POST, request, String.class);

        // Extract access token from response
        String responseBody = response.getBody();
        assert responseBody != null;
        String accessToken = extractAccessToken(responseBody);
        return accessToken;
    }

    private String extractAccessToken(String responseBody) {
        // Parse the JSON response and extract the access_token field
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            com.fasterxml.jackson.databind.JsonNode rootNode = objectMapper.readTree(responseBody);
            return rootNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing access token", e);
        }
    }

    private String fetchAccountDetails(String accessToken, String accountId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        String url = SALESFORCE_BASE_URL + "/services/data/v62.0/sobjects/Account/" + accountId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }
}
