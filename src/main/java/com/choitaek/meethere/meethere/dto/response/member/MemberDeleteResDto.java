package com.choitaek.meethere.meethere.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDeleteResDto {

    @Schema(description = "응답 메시지")
    private String message;
}
