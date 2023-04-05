package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.spotify.oauth2.api.RestResource.postAccount;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String access_token;
    private static LocalDateTime expire_time;

    public synchronized static String getToken() {
        try {
            if (access_token == null || LocalDateTime.now().isAfter(expire_time)) {
                System.out.println("Refreshing token ...");
                Response response = refreshToken();
                access_token = response.path("access_token");
                int expireInSeconds = response.path("expires_in");
                expire_time = LocalDateTime.now().plusSeconds(expireInSeconds - 300);
            } else {
                System.out.println("Token can be use");
            }

        } catch (Exception e) {
            throw new RuntimeException("Was Not Able To Refresh Token :(");
        }
        return access_token;
    }


    private static Response refreshToken() {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", ConfigLoader.getInstance().getClientId());
        params.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        params.put("refresh_token", ConfigLoader.getInstance().getClientRefreshToken());
        params.put("grant_type", ConfigLoader.getInstance().getClientGrantType());


        Response response = postAccount(params);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Refresh token failed");
        }
        return response;
    }
}
