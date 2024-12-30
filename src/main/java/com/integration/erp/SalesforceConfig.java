package com.integration.erp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "salesforce")
public class SalesforceConfig {
    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private String accountEndpoint;

    // Getters and Setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
    public String getTokenUrl() { return tokenUrl; }
    public void setTokenUrl(String tokenUrl) { this.tokenUrl = tokenUrl; }
    public String getAccountEndpoint() { return accountEndpoint; }
    public void setAccountEndpoint(String accountEndpoint) { this.accountEndpoint = accountEndpoint; }
}
