package com.choitaek.meethere.meethere.dto.share;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareObjectDto {

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름은 필수입니다.")
    private String userName;

    @Schema(description = "주소 이름")
    @NotBlank(message = "주소 이름은 필수입니다.")
    private String addressName;

    @Schema(description = "지점 이름")
    @NotBlank(message = "지점 이름은 필수입니다.")
    private String placeName;

    @Schema(description = "도로명 주소")
    @NotBlank(message = "도로명 주소는 필수입니다.")
    private String roadName;

    @Schema(description = "위도")
    @NotNull(message = "위도값은 필수입니다.")
    private double lat;

    @Schema(description = "경도")
    @NotNull(message = "경도값은 필수입니다.")
    private double lon;
}
