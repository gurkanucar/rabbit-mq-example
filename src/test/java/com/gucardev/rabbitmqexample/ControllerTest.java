package com.gucardev.rabbitmqexample;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.gucardev.rabbitmqexample.internal.InternalProcessResponseListener;
import com.gucardev.rabbitmqexample.internal.StartProcessListener;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.awaitility.Awaitility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.http.ContentType;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8443")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final static String BASE_URL = "http://localhost";
    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    @Test
    void test() throws InterruptedException {

        ch.qos.logback.classic.Logger mainLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(StartProcessListener.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        mainLogger.addAppender(listAppender);
        ch.qos.logback.classic.Logger otherLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(InternalProcessResponseListener.class);
        otherLogger.addAppender(listAppender);


        given().contentType(ContentType.JSON)
                .when().port(port)
                .baseUri(BASE_URL)
                .get("/test/grkn")
                .then().log().all()
                .assertThat().statusCode(200);



      Thread.sleep(Duration.ofSeconds(10).toMillis());
    rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.INTERNAL_QUEUE_PROCESSOR_RESPONSE_ROUTING_KEY,
            new MyData("23178dc9-5aa6-492b-8c7e-504ced896412","grkn_UPDATED"));

       // {"id":"23178dc9-5aa6-492b-8c7e-504ced896412", "name":"grkn_UPDATED"}


        String expectedLogMessage = "Message Received from internal process response queue: MyData(id=23178dc9-5aa6-492b-8c7e-504ced896412, name=grkn_UPDATED) ";


        await().atMost(Duration.ofSeconds(10)).pollInterval(Duration.ofMillis(500)).untilAsserted(() -> {
            List<ILoggingEvent> logsList = listAppender.list;
             assertTrue( logsList.stream().anyMatch(log -> log.getFormattedMessage().contains(expectedLogMessage)));
        });

    //
    //       List<ILoggingEvent> logsList = listAppender.list;
    //      assertTrue(logsList.stream().anyMatch(log ->
    // log.getFormattedMessage().contains(expectedLogMessage)));

  }
}
