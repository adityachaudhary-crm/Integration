package com.integration.erp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:jwt.properties")
})
@ConfigurationProperties(prefix = "jwt")
public class SalesforceConfigJWT {
    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private String privateKey;
    private String accountEndpoint;
    private String salesforceEndpoint;
    private String userName;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSalesforceEndpoint() {
        return salesforceEndpoint;
    }
    public void setSalesforceEndpoint(String salesforceEndpoint) {
        this.salesforceEndpoint = salesforceEndpoint;
    }
    // Getters and Setters
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
    public String getTokenUrl() { return tokenUrl; }
    public void setTokenUrl(String tokenUrl) { this.tokenUrl = tokenUrl; }
    public String getAccountEndpoint() { return accountEndpoint; }
    public void setAccountEndpoint(String accountEndpoint) { this.accountEndpoint = accountEndpoint; }
    public String getPrivateKey() { return privateKey; }
    public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }
    
}