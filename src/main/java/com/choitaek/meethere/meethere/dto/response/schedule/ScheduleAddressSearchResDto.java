package com.choitaek.meethere.meethere.dto.response.schedule;

import com.choitaek.meethere.meethere.dto.schedule.ScheduleAddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAddressSearchResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "출발 지점 리스트")
    private List<ScheduleAddressObjectDto> startAddressList;
}
