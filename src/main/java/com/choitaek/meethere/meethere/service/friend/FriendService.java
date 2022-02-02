package com.choitaek.meethere.meethere.service.friend;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.friend.FriendObjectDto;
import com.choitaek.meethere.meethere.dto.request.friend.FriendCheckReqDto;
import com.choitaek.meethere.meethere.dto.request.friend.FriendSaveReqDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendCheckResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendDeleteResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendSaveResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendSearchResDto;
import com.choitaek.meethere.meethere.entity.friend.FriendEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.repository.jpa.friend.FriendRepo;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

    private final ResponseUtil responseUtil;

    private final FriendRepo friendRepo;
    private final MemberRepo memberRepo;

    // 친구 찾기
    @Transactional(readOnly = true)
    public ResponseSuccessDto<FriendCheckResDto> checkFriend(FriendCheckReqDto friendCheckReqDto) {
        MemberEntity member = memberRepo.findOneByEmail(friendCheckReqDto.getEmail());
        if (!(member.getName().equals(friendCheckReqDto.getName()) && member.getPhone().equals(friendCheckReqDto.getPhone()))) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FriendCheckResDto friendCheckResDto = new FriendCheckResDto("친구 조회 성공", member.getUuid());
        ResponseSuccessDto<FriendCheckResDto> res = responseUtil.successResponse(friendCheckResDto);
        return res;
    }

    // 친구 추가
    public ResponseSuccessDto<FriendSaveResDto> saveFriend(FriendSaveReqDto friendSaveReqDto) {
        MemberEntity member = memberRepo.findOneByUuid(friendSaveReqDto.getMemberUuid());
        MemberEntity friendMember = memberRepo.findOneByUuid(friendSaveReqDto.getFriendUuid());
        if (friendMember == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 중복 친구추가 확인
        List<FriendEntity> friendList = friendRepo.findByMemberUuid(member.getUuid()).getContent();
        for (FriendEntity friend : friendList) {
            if (friend.getEmail().equals(friendMember.getEmail())) {
                try {
                    throw new Exception("이미 존재하는 회원입니다.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.createFriend(friendMember, member);
        friendRepo.save(friendEntity);

        FriendSaveResDto friendSaveResDto = new FriendSaveResDto("친구 추가 성공");
        ResponseSuccessDto<FriendSaveResDto> res = responseUtil.successResponse(friendSaveResDto);
        return res;
    }

    // 친구 목록
    @Transactional(readOnly = true)
    public ResponseSuccessDto<FriendSearchResDto> searchFriend(UUID memberUuid) {
        Page<FriendEntity> friendPage = friendRepo.findByMemberUuid(memberUuid);
        FriendSearchResDto friendSearchResDto = new FriendSearchResDto(
                "친구 목록 조회 성공", changeEntityToObject(friendPage.getContent())
        );
        ResponseSuccessDto<FriendSearchResDto> res = responseUtil.successResponse(friendSearchResDto);
        return res;
    }

    // 친구 삭제
    public ResponseSuccessDto<FriendDeleteResDto> deleteFriend(UUID friendUuid) {
        FriendEntity friend = friendRepo.findOneByUuid(friendUuid);
        friendRepo.delete(friend);
        FriendDeleteResDto friendDeleteResDto = new FriendDeleteResDto("친구 삭제 성공");
        ResponseSuccessDto<FriendDeleteResDto> res = responseUtil.successResponse(friendDeleteResDto);
        return res;
    }


    // FriendEntity -> FriendObjectDto
    private List<FriendObjectDto> changeEntityToObject(List<FriendEntity> friendEntityList) {
        ArrayList<FriendObjectDto> friendObjectDtoList = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntityList) {
            FriendObjectDto friendObjectDto = new FriendObjectDto(
                    friendEntity.getUuid(), friendEntity.getName(), friendEntity.getEmail(), friendEntity.getPhone()
            );
            friendObjectDtoList.add(friendObjectDto);
        }
        return friendObjectDtoList;
    }
}
