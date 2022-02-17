package com.choitaek.meethere.meethere.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleObjectResDto {

    @Schema(description = "스케쥴 uuid")
    private UUID uuid;

    @Schema(description = "스케쥴 이름")
    private String name;

    @Schema(description = "스케쥴 날짜")
    private String date;

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
