package xyz.andriuslima.canaryviewapi.api.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.andriuslima.canaryviewapi.application.values.PostType;
import xyz.andriuslima.canaryviewapi.application.views.PostView;
import xyz.andriuslima.canaryviewapi.application.views.UserProfileView;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static xyz.andriuslima.canaryviewapi.utils.RequestSpecs.jwt;
import static xyz.andriuslima.canaryviewapi.utils.Users.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = "it")
public class UserControllerIntTest {

    @BeforeAll
    static void setup(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Test
    public void getJakeProfileShouldReturnProperData() {
        var response = given()
                .spec(jwt(jake))
                .basePath("/users/{userId}/profile")
                .pathParam("userId", jake)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body().as(UserProfileView.class);

        assertEquals("jakeperalta", response.getUsername());
        assertEquals(2, response.getNumberOfPosts());
        assertEquals(2, response.getFollows());
        assertEquals(2, response.getFollowers());
    }

    @Test
    public void getBoyleProfileShouldReturnProperData() {
        var response = given()
                .spec(jwt(boyle))
                .basePath("/users/{userId}/profile")
                .pathParam("userId", boyle)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body().as(UserProfileView.class);

        assertEquals("charlesboyle", response.getUsername());
        assertEquals(1, response.getNumberOfPosts());
        assertEquals(2, response.getFollows());
        assertEquals(1, response.getFollowers());
    }

    @Test
    public void getGinaProfileShouldReturnProperData() {
        var response = given()
                .spec(jwt(gina))
                .basePath("/users/{userId}/profile")
                .pathParam("userId", gina)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body().as(UserProfileView.class);

        assertEquals("ginalinetti", response.getUsername());
        assertEquals(1, response.getNumberOfPosts());
        assertEquals(1, response.getFollows());
        assertEquals(2, response.getFollowers());
    }

    @Test
    public void getBoylePostsShouldReturnProperData() {
        List<PostView> response = given()
                .spec(jwt(gina))
                .basePath("/users/{userId}/posts")
                .pathParam("userId", boyle)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", PostView.class);

        assertEquals(1, response.size());
        assertEquals(PostType.QuoteRepost, response.get(0).getType());
        assertNotNull(response.get(0).getParent());
    }
}