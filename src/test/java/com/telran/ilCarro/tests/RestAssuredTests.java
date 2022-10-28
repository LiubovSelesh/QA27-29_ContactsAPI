package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.telran.ilCarro.dto.AuthorizationAssuredDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssuredTests {

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";
//        RestAssured.basePath = "registration";

    }

    @Test
    public void registrationPositiveTest() {

        AuthorizationAssuredDto authorizationRequestDto = AuthorizationAssuredDto.builder()
                .first_name("Pol")
                .second_name("Pol")
                .build();


        String token = "R29xNjExNjdAZ2YuY29tOkdvMTIzNDU3Ng==";
        String date = given().header("Authorization", token)
                .contentType("application/json")
                .body(authorizationRequestDto)
                .post("registration")
                .then()
                .assertThat().statusCode(200)
                .extract().path("registration_date");
        System.out.println(date);


    }
}
