package com.choitaek.meethere.meethere.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVerifyReqDto {

    @Schema(description = "인증 메일")
    private String email;

    @Schema(description = "인증 코드")
    private String code;
}
