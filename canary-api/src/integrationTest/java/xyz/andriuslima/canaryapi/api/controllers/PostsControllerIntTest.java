package xyz.andriuslima.canaryapi.api.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.andriuslima.canaryapi.application.commands.CreatePostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateQuoteRepostCommand;
import xyz.andriuslima.canaryapi.application.commands.CreateRepostCommand;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static xyz.andriuslima.canaryapi.utils.RequestSpecs.jwt;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = "it")
public class PostsControllerIntTest {

    @BeforeAll
    static void setup(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Value("${posterr.post.content.maxLen}")
    int contentMaxLen;

    @Test
    public void createPost() {
        given()
                .spec(jwt(123))
                .basePath("/posts")
                .body(new CreatePostCommand("abcde"))
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("content", is("abcde"));
    }

    @Test
    public void createPostWithExceededContentLength() {
        var content = this.generateContent(contentMaxLen + 1);
        given()
                .spec(jwt(123))
                .basePath("/posts")
                .body(new CreatePostCommand(content))
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    public void createRepost() {
        given()
                .spec(jwt(123))
                .basePath("/posts/repost")
                .body(new CreateRepostCommand(123))
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("parent", is(123));
    }

    @Test
    public void createQuoteRepost() {
        given()
                .spec(jwt(123))
                .basePath("/posts/quote")
                .body(new CreateQuoteRepostCommand("Hello!", 123))
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("parent", is(123))
                .body("content", is("Hello!"));
    }

    private String generateContent(int len) {
        return "a".repeat(len);
    }
}