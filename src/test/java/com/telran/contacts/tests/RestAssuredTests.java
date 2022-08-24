package com.telran.contacts.tests;

import com.jayway.restassured.RestAssured;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ContactRequestDto;
import com.telran.contacts.dto.GetAllContactsDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJvbisyMUBnbWFpbC5jb20ifQ._pWn7N1n8T3BpOa-s8XJfU_aa5tMsPWApGzmKgZmB2U";

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginPositiveTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("ron+21@gmail.com")
                .password("Ro1234567$")
                .build();

        LoginRegResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(LoginRegResponseDto.class);
        System.out.println(responseDto.getToken());

        String token2 = given().contentType("application/json")
                .body(requestDto)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token", equalTo(token))
                .extract().path("token");
        System.out.println(token2);
    }

    @Test
    public void loginNegativeTestWithInvalidPassword() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("ron+21@gmail.com")
                .password("Ro1234567")
                .build();

        String message = given().contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().path("message");
        System.out.println(message);

    }

    @Test
    public void addNewContactPositiveTest() {

        int i = (int) ((System.currentTimeMillis()/1000)%3600);

        ContactRequestDto contactRequestDto = ContactRequestDto.builder()
                .address("Berlin")
                .description("Friend")
                .email("qaz" + i + "@.gf.com")
                .lastName("Qaz")
                .name("Qazi")
                .phone("125896324" + i)
                .build();

        int id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactRequestDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }

    @Test
    public void getAllContactsTest() {
        GetAllContactsDto responseDto = given()
                .header("Authorization", token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDto.class);
        for (ContactRequestDto contactRequestDto: responseDto.getContacts()) {
            System.out.println(contactRequestDto.getId() + " ****** " + contactRequestDto.getLastName() + " ****** ");
            System.out.println("======================================");
        }
    }
    @Test
    public void deleteContactTest() {
        String status = given().header("Authorization", token)
                .delete("contact/5811")
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);

    }

}

