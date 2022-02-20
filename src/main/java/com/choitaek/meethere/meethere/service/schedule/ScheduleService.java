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
import com.choitaek.meethere.meethere.errorhandling.exception.service.EntityIsNullException;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import com.choitaek.meethere.meethere.repository.jpa.schedule.ScheduleAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.schedule.ScheduleRepo;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ResponseUtil responseUtil;

    private final ScheduleRepo scheduleRepo;
    private final ScheduleAddressRepo scheduleAddressRepo;
    private final MemberRepo memberRepo;

    // 스케쥴 저장
    @Transactional
    public ResponseSuccessDto<ScheduleSaveResDto> saveSchedule(ScheduleSaveReqDto scheduleSaveReqDto) {
        MemberEntity member = memberRepo.findById(scheduleSaveReqDto.getMemberUuid())
                .orElseThrow(() -> new EntityIsNullException("해당 회원이 존재하지 않습니다."));

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.createSchedule(scheduleSaveReqDto, member);
        scheduleRepo.save(schedule);

        List<ScheduleAddressSaveReqDto> startAddressList = scheduleSaveReqDto.getStartAddressList();
        for (ScheduleAddressSaveReqDto scheduleAddressSaveReqDto : startAddressList) {
            ScheduleAddressEntity scheduleAddress = new ScheduleAddressEntity();
            scheduleAddress.createScheduleAddress(scheduleAddressSaveReqDto, schedule);
            scheduleAddressRepo.save(scheduleAddress);
        }

        ScheduleSaveResDto scheduleSaveResDto = new ScheduleSaveResDto("스케쥴 저장 완료");
        ResponseSuccessDto<ScheduleSaveResDto> res = responseUtil.successResponse(scheduleSaveResDto);
        return res;
    }

    // 회원의 스케쥴 목록
    @Transactional(readOnly = true)
    public ResponseSuccessDto<ScheduleSearchResDto> searchScheduleList(UUID memberUuid) {
        MemberEntity member = memberRepo.findById(memberUuid)
                .orElseThrow(() -> new EntityIsNullException("존재하지 않는 회원입니다."));
        List<ScheduleEntity> scheduleList = scheduleRepo.findByMemberEntity(member);
        List<ScheduleObjectResDto> scheduleObjectResDtoList
                = scheduleList.stream().map(s -> new ScheduleObjectResDto(
                        s.getUuid(), s.getName(), s.getDate(), s.getAddressName(), s.getPlaceName(), s.getRoadName(), s.getLat(), s.getLon()
                ))
                .collect(Collectors.toList());

        ScheduleSearchResDto scheduleSearchResDto = new ScheduleSearchResDto("스케쥴 목록 조회 성공", scheduleObjectResDtoList);
        ResponseSuccessDto<ScheduleSearchResDto> res = responseUtil.successResponse(scheduleSearchResDto);
        return res;
    }

    // 스케쥴 - 출발 주소 리스트
    @Transactional(readOnly = true)
    public ResponseSuccessDto<ScheduleAddressSearchResDto> searchStartAddressList(UUID scheduleUuid) {
        ScheduleEntity schedule = scheduleRepo.findById(scheduleUuid)
                .orElseThrow(() -> new EntityIsNullException("존재하지 않는 스케쥴입니다."));
        List<ScheduleAddressEntity> scheduleAddressList = scheduleAddressRepo.findByScheduleEntity(schedule);
        List<ScheduleAddressObjectDto> scheduleAddressObjectDtoList = scheduleAddressList.stream().map(
                s -> new ScheduleAddressObjectDto(
                        s.getUserName(), s.getAddressName(), s.getPlaceName(), s.getRoadName(), s.getLat(), s.getLon()
                )
        ).collect(Collectors.toList());


        ScheduleAddressSearchResDto scheduleAddressSearchResDto = new ScheduleAddressSearchResDto(
                "출발 주소 목록 조회 성공", scheduleAddressObjectDtoList
        );
        ResponseSuccessDto<ScheduleAddressSearchResDto> res = responseUtil.successResponse(scheduleAddressSearchResDto);
        return res;
    }

    // 스케쥴 정보 수정
    @Transactional
    public ResponseSuccessDto<ScheduleUpdateResDto> updateSchedule(ScheduleUpdateReqDto scheduleUpdateReqDto) {
        ScheduleEntity schedule = scheduleRepo.findById(scheduleUpdateReqDto.getUuid())
                .orElseThrow(() -> new EntityIsNullException("존재하지 않는 스케쥴입니다."));
        schedule.updateSchedule(scheduleUpdateReqDto);
        ScheduleUpdateResDto scheduleUpdateResDto = new ScheduleUpdateResDto("스케쥴 정보 수정 성공");
        ResponseSuccessDto<ScheduleUpdateResDto> res = responseUtil.successResponse(scheduleUpdateResDto);
        return res;
    }

    // 스케쥴 삭제
    @Transactional
    public ResponseSuccessDto<ScheduleDeleteResDto> deleteSchedule(UUID uuid) {
        ScheduleEntity schedule = scheduleRepo.findById(uuid)
                .orElseThrow(() -> new EntityIsNullException("존재하지 않는 스케쥴입니다."));

        List<ScheduleAddressEntity> scheduleAddressList = scheduleAddressRepo.findByScheduleEntity(schedule);
        for (ScheduleAddressEntity scheduleAddress : scheduleAddressList) {
            scheduleAddressRepo.delete(scheduleAddress);
        }

        scheduleRepo.delete(schedule);
        ScheduleDeleteResDto scheduleDeleteResDto = new ScheduleDeleteResDto("스케쥴 삭제 성공");
        ResponseSuccessDto<ScheduleDeleteResDto> res = responseUtil.successResponse(scheduleDeleteResDto);
        return res;
    }
}
