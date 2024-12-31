#Author
### Aditya Chaudhary
### adityachaudhary.crm@gmail.com
### 608-692-0386

# Java - Salesforce Integration 
  Connect Java to Salesforce with Spring Boot and Salesforce's External Client Apps. Live Java application is hosted on Heroku.  

## Server to Server Authentication Flows 
-  No user interference while integrating. 

### Client Credential
- Summary: Server to Server connection using client credentials(Client Id and Client Secret) exchanged for Token. Token is sent in Header.
- Data Access is based on User under which the app is running( Run As <UserName>. 
- Host: https://adityachaudhary-crm-java-e3acd64ca5e4.herokuapp.com
- Endpoint: **jwt**//account/001aj00000mq6FQAAY"
- Salesforce-Org: https://adityachaudharycrm-dev-ed.develop.my.salesforce.com

### JWT
- #### Summary:
-   Create and upload certicate to Salesforce external client app. 
-   ( Plain Text/Base 64 Encoded ) JSON containing Client Id, Username **AND** Signature( function(JSON) created using Private Key is sent to Salesforce by Java app.
-   Salesforce validates the signature using public key (from uploaded certificate)
-   Salesforce sends back Token.
-   Note:  No client Secret is used.  Any userName who has access to App can be used( this is a negative in this flow)
- Host: https://adityachaudhary-crm-java-e3acd64ca5e4.herokuapp.com
- Endpoint: **jwt**/account/001aj00000mq6FQAAY"
- Salesforce-Org: https://adityachaudharycrm-dev-ed.develop.my.salesforce.com
