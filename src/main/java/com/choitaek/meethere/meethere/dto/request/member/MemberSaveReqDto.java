package com.choitaek.meethere.meethere.dto.request.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveReqDto {

    @Schema(description = "가입 이메일")
    private String email;

    @Schema(description = "가입 비밀번호")
    private String pw;

    @Schema(description = "가입 이름")
    private String name;

    @Schema(description = "가입 전화번호")
    private String phone;

    @Schema(description = "가입 주소")
    private AddressObjectDto addressObjectDto;
}
