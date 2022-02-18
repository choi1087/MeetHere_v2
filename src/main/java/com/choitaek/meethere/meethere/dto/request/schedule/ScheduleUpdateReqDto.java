package com.choitaek.meethere.meethere.dto.request.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateReqDto {

    @Schema(description = "스케쥴 uuid")
    @NotNull(message = "스케쥴 uuid는 필수입니다.")
    private UUID uuid;

    @Schema(description = "수정된 스케쥴 이름")
    @NotBlank(message = "수정된 스케쥴 이름은 필수입니다.")
    private String name;

    @Schema(description = "수정된 스케쥴 날짜")
    @NotBlank(message = "수정된 스케쥴 날짜는 필수입니다.")
    private String date;
}
