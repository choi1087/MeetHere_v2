package com.choitaek.meethere.meethere.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressObjectDto {

    @Schema(description = "주소 이름")
    private String addressName;

    @Schema(description = "지점 이름")
    private String placeName;

    @Schema(description = "도로명 주소")
    private String roadName;

    @Schema(description = "위도")
    private double lat;

    @Schema(description = "경도")
    private double lon;
}
