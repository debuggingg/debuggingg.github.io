---
layout: single
title: 2024-09-24-springsecurity4-kakao
---
#### KAKAO 인증 

---

# How to Use Kakao's Login OpenAPI for Easy Login

### Steps:

1. **Access Kakao Developers**: Go to the Kakao Developers page and navigate to the Kakao login section.

2. **Add a New Application**:
   - Click on [My Applications] >> [Add Application].
   - Enter the app name, company name, and category, then click save.

3. **Register Web Platform**:
   - Select the created application and go to [Platform].
   - Click on [Register Web Platform].
   - Add the site domain and click save. ex>`http://localhost:8000`

4. **Copy REST API Key**:
   - In the created application, navigate to [App Key] and copy the [REST API Key].

5. **Activate Kakao Login**:
   - Go to [Kakao Login] in the created application.
   - Change the status of [Activation Settings] to [ON].
   - Click on [Register Redirect URI] and enter the redirect URI.
   - **Redirect URI**: `http://localhost:8000/security/kakao/callback`.
   - Click save.

6. **Set Consent Items**:
   - In the created application, navigate to [Kakao Login] >>동의항목 [Consent Items].
   - Configure the personal information you want to receive in the response.

7. **Generate Client Secret**:
   - Go to [Kakao Login] >> [Security].
   - Generate and copy the Client Secret code, then set it to active.

8. **Add Required Libraries**:
   - Build the `scribejava-apis` and `json-simple` libraries into your project using Maven: update `pom.xml`.

9. **Create Necessary Classes**:
   - Write the `KakaoLoginApi` and `KakaoLoginBean` classes to request the login-related OpenAPI.

---


---

1. **Log in to Kakao Developers**: After logging in, add a new app and save the REST API Key in a notepad for future token issuance.

2. **Register the Web Platform**: Go to the "Platform" section and register a web platform with the URL `http://localhost:8000`. 

3. **Enable Activation**: Set the activation toggle to "on."

4. **Register the Redirect URI**: Add the redirect URI as `http://localhost:8000/security/kakao/callback` to enable token issuance.

5. **동의항목Set Consent Items**: Configure the response consent items according to your requirements.

6. **Security**: Generate the Client Secret code for enhanced security and save it in a notepad.

7. **Library Registration**: Add the required libraries `scribejava-apis` and `json-simple` to your `pom.xml`.

8. **Create Necessary Classes**: Write the classes `KakaoLoginApi` and `KakaoLoginBean` for implementing the login OpenAPI.

---

#### pom.xml
```xml
		<dependency>
		    <groupId>com.googlecode.json-simple</groupId>
		    <artifactId>json-simple</artifactId>
		    <version>1.1.1</version>
		</dependency>
				<!-- https://mvnrepository.com/artifact/com.github.scribejava/scribejava-apis -->
		<!-- OAuth2.0 기능을 제공하기 위한 라이브러리 -->
		<dependency>
		    <groupId>com.github.scribejava</groupId>
		    <artifactId>scribejava-apis</artifactId>
		    <version>3.3.0</version>
	<!-- 	    <scope>runtime</scope> -->
		</dependency>	
```

\
#### KakaoLoginApi.java

```java
package xyz.itwill.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

// Class that provides functionality to use Kakao OpenAPI
// => Written by extending the DefaultApi20 class
public class KakaoLoginApi extends DefaultApi20 {
    protected KakaoLoginApi() { }    

    public static class InstanceHolder {
        private static final KakaoLoginApi INSTANCE = new KakaoLoginApi();
    }
    
    public static KakaoLoginApi instance() {
        return InstanceHolder.INSTANCE;
    }
    
    @Override
    public String getAccessTokenEndpoint() {
        return "https://kauth.kakao.com/oauth/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://kauth.kakao.com/oauth/authorize";
    }

}
```

#### KakaoLoginBean.java

```java
package xyz.itwill.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

// Class for requesting the Kakao OpenAPI
@Component
public class KakaoLoginBean {
	//[REST API KEY]
    private final static String KAKAO_CLIENT_ID = "9f127646d59dd6ac2e797e6b4023";
//[CLIENT SECRET]
private final static String KAKAO_CLIENT_SECRET = "WbBVtdcJnUhpJKnTPFnk9gkwZ23nZ";
   // REDIRECT URI  address
    private final static String KAKAO_REDIRECT_URI = "http://localhost:8000/kakao/callback";
    // name of session attribute will be saved  
    private final static String SESSION_STATE = "kakao_oauth_state";
    // URL for the OpenAPI to retrieve user profile information after Kakao login
    private final static String PROFILE_API_URL = "https://kapi.kakao.com/v2/user/me";
    
    /* Method to generate and return a random value for session validation */
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    /* Method to store the random value as an attribute in the session (HttpSession object) 
    => in order to check for the client and  accesser tocken to  match   */
    private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }
    
    /* Method to return the random value stored as an attribute in the session (HttpSession object) */
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }
    
    /* Method to create a URL for the authentication process */
    public String getAuthorizationUrl(HttpSession session) {
        /* Generate a random value for session validation and store it */
        String state = generateRandomString();
        
        // Save the generated random value as a Session Scope attribute
        setSession(session, state);
        
        // Create an OAuth20Service object using the ServiceBuilder
        // => OAuth20Service object: Provides authentication based on OAuth2.0
        OAuth20Service oauth20Service = new ServiceBuilder()
                .apiKey(KAKAO_CLIENT_ID)
                .apiSecret(KAKAO_CLIENT_SECRET)
                .callback(KAKAO_REDIRECT_URI)
                .state(state)
                .build(KakaoLoginApi.instance());
        
        // Generate and return the authentication URL using the OAuth20Service object
        return oauth20Service.getAuthorizationUrl();
    }
    
    /* Method to obtain and return the access token */
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) 
            throws IOException {
        // Retrieve and store the random value for session validation
        String sessionState = getSession(session);
        if (StringUtils.pathEquals(sessionState, state)) {
            OAuth20Service oauth20Service = new ServiceBuilder()
                    .apiKey(KAKAO_CLIENT_ID)
                    .apiSecret(KAKAO_CLIENT_SECRET)
                    .callback(KAKAO_REDIRECT_URI)
                    .state(state)
                    .build(KakaoLoginApi.instance());
            OAuth2AccessToken accessToken = oauth20Service.getAccessToken(code);
            return accessToken;
        }
        return null;
    }
    
    /* Method to request the user profile using the access token and return it as a JSON string */
    public String getUserProfile(OAuth2AccessToken oAuth2AccessToken) throws IOException {
        OAuth20Service oauth20Service = new ServiceBuilder()
                .apiKey(KAKAO_CLIENT_ID)
                .apiSecret(KAKAO_CLIENT_SECRET)
                .build(KakaoLoginApi.instance());
        
        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauth20Service);
        oauth20Service.signRequest(oAuth2AccessToken, request);
        Response response = request.send();
        
        return response.getBody();
    }
}
```


#### KakaoController.java (CLASS ) 



```java
package xyz.itwill.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.RequiredArgsConstructor;
import xyz.itwill.auth.CustomUserDetails;
import xyz.itwill.auth.KakaoLoginBean;
import xyz.itwill.dto.SecurityAuth;
import xyz.itwill.dto.SecurityUser;
import xyz.itwill.service.SecurityUserService;

// OAuth2.0 functionality for authentication
// => Implementing social login features using Google, Kakao, Naver, etc.

// Using Kakao's login OpenAPI for simplified login
// 1. Visit the Kakao Developers page - Kakao login
// 2. [My Application] >> [Add Application] >> Enter app name, company name, category >> Save
// 3. Created application >> [Platform] >> Register Web platform >> Add site domain >> Save
// 4. Created application >> [App Key] >> Copy [REST API Key]
// 5. Created application >> [Kakao Login] >> Change the status in [Activation Settings] to [ON]
//    >> Register Redirect URI >> Enter Redirect URI address >> Save
// => Redirect URI address: http://localhost:8000/security/kakao/callback
// 6. Created application >> [Kakao Login] >> Set personal information to receive under [Consent Items]
// 7. Created application >> [Kakao Login] >> Issue and copy the Client Secret code
//    >> Change activation status to enabled
// 8. Add scribejava-apis library and json-simple library to the project - Maven: pom.xml
// 9. Write KakaoLoginApi class and KakaoLoginBean class to request login related OpenAPI

@Controller
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoLoginBean kakaoLoginBean;
    private final SecurityUserService securityUserService;

    // Method to handle requests to the Kakao login page
    @RequestMapping("/login")
    public String login(HttpSession session) {
        String kakaoAuthUrl = kakaoLoginBean.getAuthorizationUrl(session);
        return "redirect:" + kakaoAuthUrl;
    }

    @RequestMapping("/callback")
    public String login(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException {
        OAuth2AccessToken accessToken = kakaoLoginBean.getAccessToken(session, code, state);
        String apiResult = kakaoLoginBean.getUserProfile(accessToken);
        // apiResult = {"id":3719292067,...}
        
        // Convert JSON formatted string to Java object and store it
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(apiResult);
        Long id = (Long) jsonObject.get("id");
        JSONObject propertiesObject = (JSONObject) jsonObject.get("properties");
        String name = (String) propertiesObject.get("nickname");
        JSONObject kakaoAccountObject = (JSONObject) jsonObject.get("kakao_account");
        String email = (String) kakaoAccountObject.get("email");

        // Create an object to store user and authority information using the provided profile information
        SecurityAuth auth = new SecurityAuth();
        auth.setUserid("kakao_" + id);
        auth.setAuth("ROLE_USER");
        
        List<SecurityAuth> authList = new ArrayList<SecurityAuth>();
        authList.add(auth);
        
        SecurityUser user = new SecurityUser();
        user.setUserid("kakao_" + id);
        user.setPasswd(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setEnabled("1");
        user.setSecurityAuthList(authList);
        
        // Save Kakao login user information to SECURITY_USER and SECURITY_AUTH tables
        // => Implementing similar functionality to user registration
        if (securityUserService.getSecurityUserByUserid("kakao_" + id) == null) {
            securityUserService.addSecurityUser(user);
            securityUserService.addSecurityAuth(auth);
        }
        
        // Create UserDetails object containing the provided user information for authentication
        CustomUserDetails userDetails = new CustomUserDetails(user);
        
        // Create a UsernamePasswordAuthenticationToken object to register the user as an authenticated user in Spring Security
        // => UsernamePasswordAuthenticationToken: An object that provides the functionality to register a UserDetails object as an authenticated user in Spring Security
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        // SecurityContextHolder.getContext(): Static method returning the SecurityContext object
        // => SecurityContext object: Provides functionalities to store or retrieve authenticated user information from the session
        // SecurityContext.setAuthentication(Authentication authentication): Method to store the provided Authentication object in the session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return "redirect:/";
    }
}
```

