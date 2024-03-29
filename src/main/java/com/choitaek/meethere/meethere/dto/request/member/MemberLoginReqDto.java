package com.choitaek.meethere.meethere.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginReqDto {

    @Schema(description = "로그인 이메일")
    @NotBlank(message = "로그인 이메일은 필수입니다.")
    private String email;

    @Schema(description = "로그인 비밀번호")
    @NotBlank(message = "로그인 비밀번호는 필수입니다.")
    private String pw;
}
