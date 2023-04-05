package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCodes;
import com.spotify.oauth2.api.applicationApi.PlayListApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.makePlayListDescription;
import static com.spotify.oauth2.utils.FakerUtils.makePlayListName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth2")
@Feature("PlayList API")
public class PlayListsTests extends BaseTest {

    @Story("Create PlayList")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("777")
    @Description("Execute POST method to create a Spotify PlayList into your account")
    @Test(priority = 1, description = "verify playlist creation possibility")
    public void createPlayList() {
        Playlist playlist = playlistBuilder(makePlayListName(), makePlayListDescription(), false);
        Response response = PlayListApi.post(playlist);
        assertionStatusCode(response.statusCode(), StatusCodes.CODE_201.getCode());
        assertionVerifications(response.as(Playlist.class), playlist);
    }

    @Test(priority = 2)
    public void getPlayList() {
        //Playlist playlist = playlistBuilder(makePlayListName(), makePlayListDescription(), false);
        Playlist playlist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);
        Response response = PlayListApi.get(DataLoader.getInstance().getPlayListId());
        assertionStatusCode(response.statusCode(), StatusCodes.CODE_200.getCode());
        assertionVerifications(response.as(Playlist.class), playlist);
    }

    @Test(priority = 3)
    public void updatePlayList() {
        Playlist playlist = playlistBuilder("Updated Playlist Name", "Updated playlist description", false);
        //Playlist playlist = playlistBuilder(makePlayListName(), makePlayListDescription(), false);
        Response response = PlayListApi.update(DataLoader.getInstance().updatePlayListId(), playlist);
        assertionStatusCode(response.statusCode(), StatusCodes.CODE_200.getCode());
    }

    @Story("Create PlayList")
    @Test(priority = 4)
    public void createPlayListNegativeNoName() {
        Playlist playlist = playlistBuilder("", makePlayListDescription(), false);
        Response response = PlayListApi.post(playlist);
        assertionError(response.as(Error.class), StatusCodes.CODE_400.getCode(), StatusCodes.CODE_400.getMessage());
    }

    @Story("Create PlayList")
    @Test(priority = 5)
    public void createPlayListNegativeExpiredToken() {
        String invalid_token = "123456";
        Playlist playlist = playlistBuilder(makePlayListName(), makePlayListDescription(), false);
        Response response = PlayListApi.post(invalid_token, playlist);
        assertionStatusCode(response.statusCode(), StatusCodes.CODE_401.getCode());
        assertionError(response.as(Error.class), StatusCodes.CODE_401.getCode(), StatusCodes.CODE_401.getMessage());
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }

    @Step
    public void assertionVerifications(Playlist responsePlayList, Playlist requestPlayList){
        assertThat(responsePlayList.getName(), equalTo(requestPlayList.getName()));
        assertThat(responsePlayList.getDescription(), equalTo(requestPlayList.getDescription()));
        assertThat(responsePlayList.get_public(), equalTo(requestPlayList.get_public()));
    }

    @Step
    public void assertionStatusCode(int responseStatusCode, int requestStatusCode){
        assertThat(responseStatusCode, equalTo(requestStatusCode));
    }

    @Step
    public void assertionError(Error error, int expStatusCode, String expMessage){
        assertionStatusCode(error.getError().getStatus(), expStatusCode);
        assertThat(error.getError().getMessage(), equalTo(expMessage));
    }
}
