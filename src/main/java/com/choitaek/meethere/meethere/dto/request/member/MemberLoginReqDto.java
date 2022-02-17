package com.choitaek.meethere.meethere.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginReqDto {

    @Schema(description = "로그인 이메일")
    private String email;

    @Schema(description = "로그인 비밀번호")
    private String pw;
}
