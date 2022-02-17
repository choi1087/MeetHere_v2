package com.choitaek.meethere.meethere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    private String email;
    private String title;
    private String message;
}
