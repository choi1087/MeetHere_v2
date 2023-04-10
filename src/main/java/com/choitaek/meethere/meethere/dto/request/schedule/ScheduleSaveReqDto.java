package com.choitaek.meethere.meethere.dto.request.schedule;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSaveReqDto {

    @Schema(description = "회원 uuid")
    @NotNull(message = "회원 uuid는 필수입니다.")
    private UUID memberUuid;

    @Schema(description = "스케쥴 이름")
    @NotBlank(message = "스케쥴 이름은 필수입니다.")
    private String name;

    @Schema(description = "스케쥴 날짜")
    @NotBlank(message = "스케쥴 날짜는 필수입니다.")
    private String date;

    @Schema(description = "도착지점 주소 정보")
    private AddressObjectDto destinationAddress;

    @Schema(description = "출발지점 주소 정보 리스트")
    @NotEmpty(message = "출발지점 주소 정보는 최소 1개이상 필수입니다.")
    private List<ScheduleAddressSaveReqDto> startAddressList;
}
