package com.telran.contacts.tests;

import com.google.gson.Gson;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ErrorDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import okhttp3.*;
import org.testng.annotations.Test;

import java.io.IOException;


public class ContactOkHttpLoginTests {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void loginNegativeTestWithInvalidEmail() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("ron+21@gmailcom")
                .password("Ro1234567$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();
        ErrorDto errorDto = gson.fromJson(responseJson, ErrorDto.class);
        System.out.println(errorDto.getCode());
        System.out.println(errorDto.getMessage());
    }

    @Test
    public void loginTest() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("ron+21@gmail.com")
                .password("Ro1234567$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();

        if (response.isSuccessful()) {
            LoginRegResponseDto regResponseDto =gson.fromJson(responseJson, LoginRegResponseDto.class);
            System.out.println(regResponseDto.getToken());
//            eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJvbisyMUBnbWFpbC5jb20ifQ._pWn7N1n8T3BpOa-s8XJfU_aa5tMsPWApGzmKgZmB2U
        }else  {
            ErrorDto errorDto = gson.fromJson(responseJson, ErrorDto.class);
            System.out.println(errorDto.getCode());
            System.out.println(errorDto.getMessage());
        }

    }
}
