package xyz.andriuslima.canaryviewapi.api.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.andriuslima.canaryviewapi.application.views.PostView;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static xyz.andriuslima.canaryviewapi.utils.RequestSpecs.jwt;
import static xyz.andriuslima.canaryviewapi.utils.Users.gina;
import static xyz.andriuslima.canaryviewapi.utils.Users.jake;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = "it")
public class PostsControllerIntTest {

    @BeforeAll
    static void setup(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Test
    public void listPostsShouldReturn4() {
        List<PostView> response = given()
                .spec(jwt(jake))
                .basePath("/posts")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getList("", PostView.class);

        assertEquals(4, response.size());
    }

    @Test
    public void listGinaFollowPostsShouldReturn3() {
        List<PostView> response = given()
                .spec(jwt(gina))
                .basePath("/posts/follow")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getList("", PostView.class);

        assertEquals(3, response.size());
    }
}