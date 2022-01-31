package com.choitaek.meethere.meethere.dto.request.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateReqDto {

    @Schema(description = "회원 uuid")
    private UUID uuid;

    @Schema(description = "변경 비밀번호")
    private String pw;

    @Schema(description = "변경 확인 비밀번호")
    private String checkedPw;

    @Schema(description = "변경 이름")
    private String name;

    @Schema(description = "변경 번호")
    private String phone;

    @Schema(description = "변경 주소")
    private AddressObjectDto addressObjectDto;
}
