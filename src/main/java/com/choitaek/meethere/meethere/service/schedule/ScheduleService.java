package com.choitaek.meethere.meethere.service.schedule;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleAddressSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleUpdateReqDto;
import com.choitaek.meethere.meethere.dto.response.schedule.*;
import com.choitaek.meethere.meethere.dto.schedule.ScheduleAddressObjectDto;
import com.choitaek.meethere.meethere.dto.schedule.ScheduleObjectResDto;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.entity.schedule.ScheduleAddressEntity;
import com.choitaek.meethere.meethere.entity.schedule.ScheduleEntity;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import com.choitaek.meethere.meethere.repository.jpa.schedule.ScheduleAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.schedule.ScheduleRepo;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ResponseUtil responseUtil;

    private final ScheduleRepo scheduleRepo;
    private final ScheduleAddressRepo scheduleAddressRepo;
    private final MemberRepo memberRepo;

    // 스케쥴 저장
    public ResponseSuccessDto<ScheduleSaveResDto> saveSchedule(ScheduleSaveReqDto scheduleSaveReqDto) {
        UUID memberUuid = scheduleSaveReqDto.getMemberUuid();
        MemberEntity member = memberRepo.findOneByUuid(memberUuid);

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.createSchedule(scheduleSaveReqDto, member);
        scheduleRepo.save(schedule);

        List<ScheduleAddressSaveReqDto> startAddressList = scheduleSaveReqDto.getStartAddressList();
        for (ScheduleAddressSaveReqDto scheduleAddressSaveReqDto : startAddressList) {
            ScheduleAddressEntity scheduleAddress = new ScheduleAddressEntity();
            scheduleAddress.createScheduleAddress(scheduleAddressSaveReqDto);
            scheduleAddressRepo.save(scheduleAddress);
        }

        ScheduleSaveResDto scheduleSaveResDto = new ScheduleSaveResDto("스케쥴 저장 완료");
        ResponseSuccessDto<ScheduleSaveResDto> res = responseUtil.successResponse(scheduleSaveResDto);
        return res;
    }

    // 회원의 스케쥴 목록
    public ResponseSuccessDto<ScheduleSearchResDto> searchScheduleList(UUID memberUuid) {
        Page<ScheduleEntity> schedulePage = scheduleRepo.findByMemberUuid(memberUuid);
        Page<ScheduleObjectResDto> scheduleObjectResDtoMap = schedulePage.map(
                s -> new ScheduleObjectResDto(s.getUuid(), s.getName(), s.getDate(),
                        s.getAddressName(), s.getPlaceName(), s.getRoadName(), s.getLat(), s.getLon())
        );
        ScheduleSearchResDto scheduleSearchResDto = new ScheduleSearchResDto("스케쥴 목록 조회 성공", scheduleObjectResDtoMap.getContent());
        ResponseSuccessDto<ScheduleSearchResDto> res = responseUtil.successResponse(scheduleSearchResDto);
        return res;
    }

    // 스케쥴 - 출발 주소 리스트
    public ResponseSuccessDto<ScheduleAddressSearchResDto> searchStartAddressList(UUID scheduleUuid) {
        Page<ScheduleAddressEntity> scheduleAddressPage = scheduleAddressRepo.findByScheduleUuid(scheduleUuid);
        Page<ScheduleAddressObjectDto> scheduleAddressObjectMap = scheduleAddressPage.map(
                s -> new ScheduleAddressObjectDto(s.getUserName(), s.getAddressName(), s.getPlaceName(),
                        s.getRoadName(), s.getLat(), s.getLat())
        );
        ScheduleAddressSearchResDto scheduleAddressSearchResDto = new ScheduleAddressSearchResDto(
                "출발 주소 목록 조회 성공", scheduleAddressObjectMap.getContent()
        );
        ResponseSuccessDto<ScheduleAddressSearchResDto> res = responseUtil.successResponse(scheduleAddressSearchResDto);
        return res;
    }

    // 스케쥴 정보 수정
    public ResponseSuccessDto<ScheduleUpdateResDto> updateSchedule(ScheduleUpdateReqDto scheduleUpdateReqDto) {
        ScheduleEntity schedule = scheduleRepo.findOneByUuid(scheduleUpdateReqDto.getUuid());
        schedule.updateSchedule(scheduleUpdateReqDto);
        ScheduleUpdateResDto scheduleUpdateResDto = new ScheduleUpdateResDto("스케쥴 정보 수정 성공");
        ResponseSuccessDto<ScheduleUpdateResDto> res = responseUtil.successResponse(scheduleUpdateResDto);
        return res;
    }

    // 스케쥴 삭제
    public ResponseSuccessDto<ScheduleDeleteResDto> deleteSchedule(UUID uuid) {
        ScheduleEntity schedule = scheduleRepo.findOneByUuid(uuid);
        scheduleRepo.delete(schedule);

        List<ScheduleAddressEntity> scheduleAddressList = scheduleAddressRepo.findByScheduleUuid(schedule.getUuid()).getContent();
        for (ScheduleAddressEntity scheduleAddress : scheduleAddressList) {
            scheduleAddressRepo.delete(scheduleAddress);
        }

        ScheduleDeleteResDto scheduleDeleteResDto = new ScheduleDeleteResDto("스케쥴 삭제 성공");
        ResponseSuccessDto<ScheduleDeleteResDto> res = responseUtil.successResponse(scheduleDeleteResDto);
        return res;
    }
}
