package com.choitaek.meethere.meethere.dto.request.schedule;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSaveReqDto {

    @Schema(description = "회원 uuid")
    private UUID memberUuid;

    @Schema(description = "스케쥴 이름")
    private String name;

    @Schema(description = "스케쥴 날짜")
    private String date;

    @Schema(description = "도착지점 주소 정보")
    private AddressObjectDto destinationAddress;

    @Schema(description = "출발지점 주소 정보 리스트")
    private List<ScheduleAddressSaveReqDto> startAddressList;
}
