package com.gucardev.rabbitmqexample;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8443")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {


    @LocalServerPort
    int port;

    private final static String BASE_URL = "http://localhost";


    @BeforeAll
    void setup(){

    }


    @Test
    void test(){
        given().contentType(ContentType.JSON)
                .when().port(port)
                .baseUri(baseURI)
                .get("/test/grkn")
                .then().log().all()
                .assertThat().statusCode(200);



    }

}
