package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Paths.API_PATH;
import static com.spotify.oauth2.api.Paths.TOKEN_PATH;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String token, Object requestPlayList) {
        return given(getRequestSpec()).
                body(requestPlayList).
                auth().oauth2(token).
                when().post(path).
                then().spec(getResponseSpec()).
                extract().response();
    }

    public static Response postAccount(HashMap<String, String> params) {
        return given(getAccountRequestSpec()).
                formParams(params).
                when().post(API_PATH + TOKEN_PATH).
                then().spec(getResponseSpec()).extract().response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpec()).
                auth().oauth2(token).
                when().
                get(path).//5oasBGPesdBmMb03uzQL6S
                        then().
                spec(getResponseSpec()).
                extract().response();
    }

    public static Response update(String path, String token, Object requestPlayList){
        return given(getRequestSpec()).auth().oauth2(token).body(requestPlayList).
                when().put(path).//5oasBGPesdBmMb03uzQL6S
                        then().spec(getResponseSpec()).extract().response();
    }
}
