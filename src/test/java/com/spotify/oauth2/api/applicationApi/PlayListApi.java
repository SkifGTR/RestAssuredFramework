package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Paths.PLAYLISTS_PATH;
import static com.spotify.oauth2.api.Paths.USERS_PATH;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlayListApi {
    @Step
    public static Response post(Playlist requestPlayList) {
        return RestResource.post(USERS_PATH + "/" + ConfigLoader.getInstance().getClientUserId() + PLAYLISTS_PATH, getToken(), requestPlayList);
    }

    public static Response post(String token, Playlist requestPlayList) {
        return RestResource.post(USERS_PATH + "/" + ConfigLoader.getInstance().getClientUserId() + PLAYLISTS_PATH, token, requestPlayList);
    }

    public static Response get(String playListId) {
        return RestResource.get(PLAYLISTS_PATH + "/" + playListId, getToken());
    }

    public static Response update(String playlistId, Playlist requestPlayList) {
        return RestResource.update(PLAYLISTS_PATH + "/" + playlistId, getToken(), requestPlayList);
    }
}
