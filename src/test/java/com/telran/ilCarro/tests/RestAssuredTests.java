package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.telran.ilCarro.dto.AuthorizationAssuredDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RestAssuredTests {

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";
//        RestAssured.basePath = "registration";
    }

    @Test
    public void registrationPositiveTest() {

        int i = (int) ((System.currentTimeMillis()/1000)%3600);
        AuthorizationAssuredDto requestDto = AuthorizationAssuredDto.builder()
                .first_name("Qaq"+ i)
                .second_name("Qaq" + i)
                .email("qaqi@gmail.com")
                .password("Qq123456$")
                .build();

        AuthorizationAssuredDto responseDto = RestAssured.given()
                .contentType("application/json")
//                .body(requestDto)
                .post("registration")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthorizationAssuredDto.class);
        System.out.println(responseDto.getFirst_name());



    }
}
