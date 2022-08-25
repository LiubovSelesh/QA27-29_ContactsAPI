package com.telran.ilCarro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class DeletedUserAssuredDto {
    String email;
    String token;


}
