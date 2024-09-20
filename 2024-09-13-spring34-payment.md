---
layout: single
title: 2024-09-13-spring34-payment
---


### `Payment.java DTO`

```java
package xyz.itwill09.dto;

import lombok.Data;

@Data
public class Payment {
    private String impUid; // Transaction unique number
    private String merchantUid; // Merchant order number
    private long amount; // Payment amount
    private String payDate; // Payment date
    private String userid; // ID of the payment user
    private String status; // Payment status
}
```

### `PaymentService.java`

```java
package xyz.itwill09.service;

import xyz.itwill09.dto.Payment;

public interface PaymentService {
    void addPayment(Payment payment); // Method to add payment information
    String getAccessToken(); // Method to get access token
    Payment getPayment(String accessToken, String impUid); // Method to get payment information
    String cancelPayment(String accessToken, Payment payment); // Method to cancel payment
}
```

### `PaymentServiceImpl.java`

```java
package xyz.itwill09.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import xyz.itwill09.dto.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void addPayment(Payment payment) {
        System.out.println("Executing command to insert payment information into the PAYMENT table - calling DAO class method");
    }

    // Method to get an access token for using OpenAPI
    @Override
    public String getAccessToken() {
        String accessToken = "";

        // URL to request token from OpenAPI
        String apiURL = "https://api.iamport.kr/users/getToken";

        // JSON string for the request data
        String data = "{\"imp_key\":\"3062276086248286\",\"imp_secret\":\"9YWzU9PnaypGc7vihzgdARy5ADfRfFqTej93I8xjdAqUzsD6CpP86JneEVOeyScInXLwj1w8gLvpjs1B\"}";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream out = connection.getOutputStream()) {
                byte[] requestData = data.getBytes();
                out.write(requestData);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String input;
                String result = "";
                while ((input = br.readLine()) != null) {
                    result += input;
                }
                br.close();

                // Parse JSON response
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject responseObject = (JSONObject) jsonObject.get("response");

                accessToken = (String) responseObject.get("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    // Method to retrieve payment information using OpenAPI
    @Override
    public Payment getPayment(String accessToken, String impUid) {
        Payment payment = new Payment();

        // URL to get payment information
        String apiURL = "https://api.iamport.kr/payments/" + impUid;

        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", accessToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String input;
                String result = "";
                while ((input = br.readLine()) != null) {
                    result += input;
                }
                br.close();

                // Parse JSON response
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject responseObject = (JSONObject) jsonObject.get("response");

                payment.setImpUid((String) responseObject.get("imp_uid"));
                payment.setMerchantUid((String) responseObject.get("merchant_uid"));
                payment.setAmount((Long) responseObject.get("amount"));
                payment.setStatus((String) responseObject.get("status"));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }

    // Method to cancel a payment using OpenAPI
    @Override
    public String cancelPayment(String accessToken, Payment payment) {
        String apiURL = "https://api.iamport.kr/payments/cancel";

        // JSON string for the request data
        String data = "{\"imp_uid\" : \"" + payment.getImpUid() + "\", \"checksum\" : \"" + payment.getAmount() + "\"}";

        String resultValue = "";
        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", accessToken);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream out = connection.getOutputStream()) {
                byte[] requestData = data.getBytes();
                out.write(requestData);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                resultValue = "success";
            } else {
                resultValue = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultValue;
    }
}
```

### `PaymentController.java`

```java
package xyz.itwill09.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import xyz.itwill09.dto.Payment;
import xyz.itwill09.service.PaymentService;

@Controller
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    // Method to return the payment page
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String pay() {
        return "pay";
    }

    // Method to validate the payment amount before processing
    // Stores the order number as a session attribute and the payment amount as its value
    // Returns "ok" as a response
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ResponseBody
    public String pay(@RequestBody Payment payment, HttpSession session) {
        session.setAttribute(payment.getMerchantUid(), payment.getAmount());
        return "ok";
    }

    // Method to verify the payment amount after the transaction
    // Issues an access token, retrieves payment information using the OpenAPI,
    // and validates the payment amount
    // Returns "success" if validation is successful, otherwise "forgery"
    @RequestMapping(value = "/complate", method = RequestMethod.POST)
    @ResponseBody
    public String complate(@RequestBody Payment payment, HttpSession session) {
        // Get and store the access token
        String accessToken = paymentService.getAccessToken();

        // Use the access token and transaction ID to retrieve payment information from OpenAPI
        Payment returnPayment = paymentService.getPayment(accessToken, payment.getImpUid());

        // Retrieve and remove the stored payment amount from the session
        Long beforeAmount = (Long) session.getAttribute(payment.getMerchantUid());
        session.removeAttribute(payment.getMerchantUid());

        // Get the payment amount from the retrieved payment information
        Long amount = returnPayment.getAmount();

        if (beforeAmount.equals(amount)) { // Validation success
            paymentService.addPayment(returnPayment); // Insert the payment record into the table
            return "success";
        } else { // Validation failed
            paymentService.cancelPayment(accessToken, returnPayment); // Cancel the payment
            return "forgery";
        }
    }
}
```

### Summary

- **`pay()`**: Returns the view name for the payment page.
- **`pay(@RequestBody Payment payment, HttpSession session)`**: Handles POST requests to store the payment amount in the session for validation later, and responds with "ok".
- **`complate(@RequestBody Payment payment, HttpSession session)`**: Handles POST requests to validate the payment after the transaction, compares the stored amount with the actual payment amount, and returns "success" or "forgery" based on the validation result.

Here's a clear and organized summary of the information about integrating with the Portone API for payments, including how to obtain an access token and use it in your Java code:

---

### Portone API Integration Overview

**1. Getting Started with Portone API:**
To use the Portone API, you first need to sign up and obtain an access token. This token is essential for making authenticated API requests.

**2. Accessing the API:**
- **Website for More Information:** [Portone API Documentation](https://portone.io/korea/ko?gad_source=1&gclid=CjwKCAjwooq3BhB3EiwAYqYoEhM17OjEmO01KkHebW3Psj3EegbCdBqY3kLfZ0gsvAbnkBgxc0O7sRoCk14QAvD_BwE)
- **Integration Guide:** Use the V1 API for integration with payment gateways such as Kakao and KG Inicis. Detailed usage instructions can be found at [Portone API Documentation](https://developers.portone.io/api/rest-v1/auth?v=v1#post%20%2Fusers%2FgetToken).

**3. Obtaining an Access Token:**

To interact with the Portone API, you need to obtain an access token. The process involves sending a POST request to the token endpoint with your `imp_key` and `imp_secret`.

**Example Code for Requesting a Token:**
```java
String data = "{\"imp_key\":\"3062276086248286\",\"imp_secret\":\"9YWzU9PnaypGc7vihzgdARy5ADfRfFqTej93I8xjdAqUzsD6CpP86JneEVOeyScInXLwj1w8gLvpjs1B\"}";
```

**Response Example:**
```json
{
    "code": 0,
    "message": null,
    "response": {
        "access_token": "b2f8df82b2f13c60dfcd9baf400d6fed62f614bf",
        "now": 1726128336,
        "expired_at": 1726130136
    }
}
```

- **`access_token`:** The token you will use to authenticate your API requests.
- **`now`:** The current timestamp when the token was issued.
- **`expired_at`:** The timestamp when the token will expire.

**4. Using the Token in Your Java Code:**

Once you have obtained the access token, you can use it to make API requests. Implement this in your Java code by passing the token in the authorization header of your HTTP requests.

**Example Java Method to Get Access Token:**
```java
public String getAccessToken() {
    String accessToken = "";
    String apiURL = "https://api.iamport.kr/users/getToken";
    String data = "{\"imp_key\":\"YOUR_IMP_KEY\",\"imp_secret\":\"YOUR_IMP_SECRET\"}";

    try {
        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream out = connection.getOutputStream()) {
            byte[] requestData = data.getBytes();
            out.write(requestData);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null) {
                result.append(input);
            }
            br.close();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
            JSONObject responseObject = (JSONObject) jsonObject.get("response");
            accessToken = (String) responseObject.get("access_token");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return accessToken;
}
```

This method sends a POST request to the API to obtain the access token, processes the response, and extracts the token for use in subsequent API requests.

---

