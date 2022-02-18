package com.choitaek.meethere.meethere.dto.request.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveReqDto {

    @Schema(description = "가입 이메일")
    @NotBlank(message = "가입 이메일은 필수입니다.")
    private String email;

    @Schema(description = "가입 비밀번호")
    @NotBlank(message = "가입 비밀번호는 필수입니다.")
    private String pw;

    @Schema(description = "가입 이름")
    @NotBlank(message = "가입 이름은 필수입니다.")
    private String name;

    @Schema(description = "가입 전화번호")
    @NotBlank(message = "가입 전화번호는 필수입니다.")
    private String phone;

    @Schema(description = "가입 주소")
    @NotBlank(message = "가입 주소는 필수입니다.")
    private AddressObjectDto addressObjectDto;
}
