package com.choitaek.meethere.meethere.dto.request.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateReqDto {

    @Schema(description = "스케쥴 uuid")
    private UUID uuid;

    @Schema(description = "수정된 스케쥴 이름")
    private String name;

    @Schema(description = "수정된 스케쥴 날짜")
    private String date;
}
