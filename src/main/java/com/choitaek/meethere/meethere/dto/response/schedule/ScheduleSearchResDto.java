package com.choitaek.meethere.meethere.dto.response.schedule;

import com.choitaek.meethere.meethere.dto.schedule.ScheduleObjectResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSearchResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "스케쥴 목록")
    private List<ScheduleObjectResDto> scheduleList;
}
