package com.choitaek.meethere.meethere.dto.request.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateReqDto {

    @Schema(description = "회원 uuid")
    @NotNull(message = "회원 uuid는 필수입니다.")
    private UUID uuid;

    @Schema(description = "변경 비밀번호")
    @NotBlank(message = "변경 비밀번호는 필수입니다.")
    private String pw;

    @Schema(description = "변경 확인 비밀번호")
    @NotBlank(message = "변경 확인 비밀번호는 필수입니다.")
    private String checkedPw;

    @Schema(description = "변경 이름")
    @NotBlank(message = "변경 이름은 필수입니다.")
    private String name;

    @Schema(description = "변경 번호")
    @NotBlank(message = "변경 번호는 필수입니다.")
    private String phone;

    @Schema(description = "변경 주소")
    private AddressObjectDto addressObjectDto;
}
