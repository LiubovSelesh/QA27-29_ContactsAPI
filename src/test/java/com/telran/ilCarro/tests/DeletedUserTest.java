package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.RequestSpecification;
import com.telran.contacts.dto.ContactRequestDto;
import com.telran.ilCarro.dto.AuthorizationAssuredDto;
import com.telran.ilCarro.dto.UserBaseAssuredDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;

public class DeletedUserTest {

    String token = "R29xNjExNjdAZ2YuY29tOkdvMTIzNDU3Ng==";

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";

        UserBaseAssuredDto userBaseAssuredDto = UserBaseAssuredDto.builder()
                .first_name("Shon")
                .second_name("Pol")
                .build();


        String token = "R29xNjExNjdAZ2YuY29tOkdvMTIzNDU3Ng==";
        String name = given().header("Authorization", token)
                .contentType("application/json")
                .body(userBaseAssuredDto)
                .put("user")
                .then()
                .assertThat().statusCode(200)
                .extract().path("first_name");
    }

    @Test
    public void deleteUserTest() {

        given().header("Authorization", token)
                .delete("user")
                .then()
                .assertThat().statusCode(200)
                .extract().statusCode();


    }
}
