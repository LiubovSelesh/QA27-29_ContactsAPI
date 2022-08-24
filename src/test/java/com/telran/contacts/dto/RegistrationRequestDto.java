package com.telran.contacts.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class RegistrationRequestDto {

    String email;
    String password;
}