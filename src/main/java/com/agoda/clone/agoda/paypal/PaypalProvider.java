package com.agoda.clone.agoda.paypal;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PaypalProvider {
    
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.base}")
    private String base;
    private RestTemplate restTemplate = new RestTemplate();

    public String createOrder(int offerId, double amount) {
        try{
            String accessToken = new JSONObject(generatePaypalAccessToken().getBody().toString()).getString("access_token");
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            double value = amount * 1.1;
            String jsonString = 
                " ["+
                    "{"+
                        "amount: {"+
                            "currency_code: 'HKD',"+
                            "value: '"+value+"',"+
                        "},"+
                    "},"+
                "] ";
            JSONArray bodyArray = new JSONArray(jsonString);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("intent", "CAPTURE");
            jsonBody.put("purchase_units", bodyArray);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);
            ResponseEntity<String> rs = restTemplate.postForEntity(new URI(base + "/v2/checkout/orders"), entity, String.class);
            return new JSONObject(rs.getBody().toString()).getString("id") ;
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public String capturePayment(String orderId){
        try{
            String accessToken = new JSONObject(generatePaypalAccessToken().getBody().toString()).getString("access_token");
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<String> rs = restTemplate.postForEntity(new URI(base + "/v2/checkout/orders/"+orderId+"/capture"), entity, String.class);
            return rs.getBody().toString();

        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<?> generatePaypalAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(generateBase64String());
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type","client_credentials");
            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> rs = restTemplate.postForEntity(new URI(base + "/v1/oauth2/token"), entity, String.class);
            return ResponseEntity.ok().body(rs.getBody());
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
        
        return null;
    }

    private String generateBase64String(){
        try {
            String encodedString = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes("UTF-8"));
            return encodedString;
        }   catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
