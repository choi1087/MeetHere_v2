package com.choitaek.meethere.meethere.dto.response.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchResDto {

    @Schema(description = "회원 uuid")
    private UUID uuid;

    @Schema(description = "회원 이메일")
    private String email;

    @Schema(description = "회원 비밀번호")
    private String pw;

    @Schema(description = "회원 이름")
    private String name;

    @Schema(description = "회원 전화번호")
    private String phone;

    @Schema(description = "회원 주소 정보")
    private AddressObjectDto addressObjectDto;
}
