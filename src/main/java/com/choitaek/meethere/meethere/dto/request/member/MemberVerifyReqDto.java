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
public class MemberVerifyReqDto {

    @Schema(description = "인증 메일")
    @NotBlank(message = "인증 메일은 필수입니다.")
    private String email;

    @Schema(description = "인증 번호")
    @NotNull(message = "인증 번호는 필수입니다.")
    private int authNum;
}
