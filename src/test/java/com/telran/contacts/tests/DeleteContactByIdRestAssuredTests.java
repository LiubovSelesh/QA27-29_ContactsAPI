package com.telran.contacts.tests;

import com.jayway.restassured.RestAssured;
import com.telran.contacts.dto.ContactRequestDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class DeleteContactByIdRestAssuredTests {

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJvbisyMUBnbWFpbC5jb20ifQ._pWn7N1n8T3BpOa-s8XJfU_aa5tMsPWApGzmKgZmB2U";
    int id;

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";

        int i = (int) ((System.currentTimeMillis()/1000)%3600);

        ContactRequestDto contactRequestDto = ContactRequestDto.builder()
                .address("Berlin")
                .description("Friend")
                .email("qaz" + i + "@.gf.com")
                .lastName("Qaz")
                .name("Qazi")
                .phone("125896324" + i)
                .build();

         id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactRequestDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");

    }

    @Test
    public void deleteByIdTest() {
        String status = given().header("Authorization", token)
                .delete("contact/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
    }
}
