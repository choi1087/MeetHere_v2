package com.choitaek.meethere.meethere.service.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import com.choitaek.meethere.meethere.dto.MailDto;
import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberLoginReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberVerifyReqDto;
import com.choitaek.meethere.meethere.dto.response.member.*;
import com.choitaek.meethere.meethere.entity.member.MemberAddressEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.exception.ApiRequestException;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import com.choitaek.meethere.meethere.service.mail.MailService;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ResponseUtil responseUtil;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "YOUR_EMAIL_ADDRESS";

    private final MemberRepo memberRepo;
    private final MemberAddressRepo memberAddressRepo;

    private final MailService mailService;

    // 회원가입
    @Transactional
    public ResponseSuccessDto<MemberSaveResDto> save(MemberSaveReqDto memberSaveReqDto) {

        // 중복 회원 검증
        if (!validateDuplicateMember(memberSaveReqDto)) {
            return responseUtil.successResponse(new MemberSaveResDto("회원가입 실패"));
        }

        // 회원 정보
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.createMember(memberSaveReqDto, passwordEncoder.encode(memberSaveReqDto.getPw()));

        // 회원 주소 정보
        MemberAddressEntity memberAddressEntity = new MemberAddressEntity();
        memberAddressEntity.createMemberAddress(memberSaveReqDto.getAddressObjectDto(), memberEntity);

        memberRepo.save(memberEntity);
        memberAddressRepo.save(memberAddressEntity);

        MailDto mailDto = mailService.verification(memberEntity.getEmail(), memberEntity.getName(), memberEntity.getAuthNum());
        mailService.mailSend(mailDto);

        MemberSaveResDto memberSaveResDto = new MemberSaveResDto("인증번호 발송");
        ResponseSuccessDto<MemberSaveResDto> res = responseUtil.successResponse(memberSaveResDto);
        return res;
    }

    // 회원 활성화 (인증 성공시)
    @Transactional
    public ResponseSuccessDto<MemberVerifyResDto> activateMember(MemberVerifyReqDto memberVerifyReqDto) {
        ResponseSuccessDto<MemberVerifyResDto> res;
        MemberEntity member = memberRepo.findByEmail(memberVerifyReqDto.getEmail())
                .orElseThrow(() -> new ApiRequestException("해당 회원이 존재하지 않습니다."));

        if (member.getAuthNum() == memberVerifyReqDto.getAuthNum()) {
            member.updateIsActive(true);
            res = responseUtil.successResponse(new MemberVerifyResDto("인증 성공"));
            return res;
        }
        res = responseUtil.successResponse(new MemberVerifyResDto("인증 실패"));
        return res;
    }

    // 로그인
    @Transactional
    public ResponseSuccessDto<MemberLoginResDto> login(MemberLoginReqDto memberLoginReqDto) {
        MemberEntity member = memberRepo.findByEmail(memberLoginReqDto.getEmail()).orElseThrow(() -> new ApiRequestException("해당 회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(memberLoginReqDto.getPw(), member.getPw())) {
            return responseUtil.successResponse(new ApiRequestException("로그인 실패"));
        }

        MemberLoginResDto memberLoginResDto = new MemberLoginResDto("로그인 성공");
        ResponseSuccessDto<MemberLoginResDto> res = responseUtil.successResponse(memberLoginResDto);
        return res;
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDto<Page<MemberSearchResDto>> findMembers() {
        Page<MemberEntity> page = memberRepo.findAll(PageRequest.of(0, 20));
        Page<MemberSearchResDto> toMap = page.map(this::getMemberSearchResDto);
        ResponseSuccessDto<Page<MemberSearchResDto>> res = responseUtil.successResponse(toMap);
        return res;
    }

    // 회원 이메일 찾기 (이름, 휴대전화)
    @Transactional(readOnly = true)
    public ResponseSuccessDto<MemberFindEmailResDto> findMemberEmail(String name, String phone) {
        MemberEntity member = memberRepo.findByNameAndPhone(name, phone).orElseThrow(() -> new ApiRequestException("해당 회원이 존재하지 않습니다"));
        MemberFindEmailResDto memberFindEmailResDto = new MemberFindEmailResDto("회원 조회 성공", member.getEmail());
        ResponseSuccessDto<MemberFindEmailResDto> res = responseUtil.successResponse(memberFindEmailResDto);
        return res;
    }

    // 회원 비밀번호 찾기 (이메일, 이름, 휴대전화)
    @Transactional
    public ResponseSuccessDto<MemberFindPwResDto> findMemberPw(String email, String name, String phone) {
        MemberEntity findMember = memberRepo.findByEmailAndNameAndPhone(email, name, phone).orElseThrow(
                () -> new ApiRequestException("해당 회원이 존재하지 않습니다.")
        );

        MailDto mailDto = mailService.createMailAndChangePassword(findMember);
        mailService.mailSend(mailDto);

        MemberFindPwResDto memberFindPwResDto = new MemberFindPwResDto(
                "회원 조회 성공", "비밀번호가 임시 비밀번호로 변경되었습니다. 임시 비밀번호는 해당 계정의 이메일로 발송하였습니다."
        );
        ResponseSuccessDto<MemberFindPwResDto> res = responseUtil.successResponse(memberFindPwResDto);
        return res;
    }

    // 회원 정보 수정
    @Transactional
    public ResponseSuccessDto<MemberUpdateResDto> updateMember(MemberUpdateReqDto memberUpdateReqDto) {
        if (!memberUpdateReqDto.getPw().equals(memberUpdateReqDto.getCheckedPw())) {
            throw new ApiRequestException("비밀번호가 일치하지 않습니다.");
        }

        MemberEntity member = memberRepo.findById(memberUpdateReqDto.getUuid()).orElseThrow(() -> new ApiRequestException("존재하지 않는 회원입니다."));
        member.updateMember(memberUpdateReqDto, passwordEncoder.encode(memberUpdateReqDto.getPw()));

        MemberUpdateResDto memberUpdateResDto = new MemberUpdateResDto("수정 완료");
        ResponseSuccessDto<MemberUpdateResDto> res = responseUtil.successResponse(memberUpdateResDto);
        return res;
    }

    // 회원 탈퇴
    @Transactional
    public ResponseSuccessDto<MemberDeleteResDto> deleteMember(UUID uuid) {
        MemberEntity member = memberRepo.findById(uuid).orElseThrow(() -> new ApiRequestException("존재하지 않는 회원입니다."));
        MemberAddressEntity memberAddress = memberAddressRepo.findOneByMemberEntity(member);
        memberAddressRepo.delete(memberAddress);
        memberRepo.delete(member);

        MemberDeleteResDto memberDeleteResDto = new MemberDeleteResDto("삭제 완료");
        ResponseSuccessDto<MemberDeleteResDto> res = responseUtil.successResponse(memberDeleteResDto);
        return res;
    }

    // 중복 회원 확인
    private Boolean validateDuplicateMember(MemberSaveReqDto memberSaveReqDto) {
        // 중복 이메일 확인
        Optional<MemberEntity> byEmail = memberRepo.findByEmail(memberSaveReqDto.getEmail());
        if (byEmail.isEmpty()) {
            return true;
        }

        MemberEntity member = byEmail.get();

        // 인증 절차가 안된 이메일이라면, 이전 데이터 삭제
        if (!member.getIsActive()) {
            memberRepo.delete(member);
        } else {
            return false;
        }

        // 중복 이름, 번호
        List<MemberEntity> findMember = memberRepo.findByName(memberSaveReqDto.getName());
        for (MemberEntity memberEntity : findMember) {
            if (memberEntity.getPhone().equals(memberSaveReqDto.getPhone())) {
                return false;
            }
        }
        return true;
    }

    // MemberEntity -> MemberSearchResDto
    private MemberSearchResDto getMemberSearchResDto(MemberEntity member) {
        MemberSearchResDto memberSearchResDto = new MemberSearchResDto(
                member.getUuid(), member.getEmail(), member.getPw(), member.getName(), member.getPhone(),
                changeMemberEntityToDto(memberAddressRepo.findOneByMemberEntity(member))
        );
        return memberSearchResDto;
    }

    // MemberAddressEntity -> AddressObjectDto
    private AddressObjectDto changeMemberEntityToDto(MemberAddressEntity memberAddressEntity) {
        AddressObjectDto addressObjectDto = new AddressObjectDto(
                memberAddressEntity.getAddressName(),
                memberAddressEntity.getPlaceName(),
                memberAddressEntity.getRoadName(),
                memberAddressEntity.getLat(),
                memberAddressEntity.getLat()
        );
        return addressObjectDto;
    }
}