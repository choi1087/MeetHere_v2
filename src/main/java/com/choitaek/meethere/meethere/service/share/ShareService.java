package com.choitaek.meethere.meethere.service.share;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.share.ShareSaveReqDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSaveResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchDestinationResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchStartResDto;
import com.choitaek.meethere.meethere.dto.share.ShareObjectDto;
import com.choitaek.meethere.meethere.entity.share.ShareAddressEntity;
import com.choitaek.meethere.meethere.entity.share.ShareEntity;
import com.choitaek.meethere.meethere.errorhandling.exception.service.EntityIsNullException;
import com.choitaek.meethere.meethere.errorhandling.exception.service.ResourceNotFoundException;
import com.choitaek.meethere.meethere.repository.jpa.share.ShareAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.share.ShareRepo;
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
public class ShareService {

    private final ResponseUtil responseUtil;

    private final ShareRepo shareRepo;
    private final ShareAddressRepo shareAddressRepo;

    // 공유코드 저장
    @Transactional
    public ResponseSuccessDto<ShareSaveResDto> saveShare(ShareSaveReqDto shareSaveReqDto) {
        // 공유코드 저장
        ShareEntity share = new ShareEntity();
        share.createShare(shareSaveReqDto);
        shareRepo.save(share);

        // 공유코드 - 출발주소 리스트 저장
        List<ShareObjectDto> startAddressList = shareSaveReqDto.getStartAddressList();
        for (ShareObjectDto shareObjectDto : startAddressList) {
            ShareAddressEntity shareAddress = new ShareAddressEntity();
            shareAddress.createShareAddress(shareObjectDto, share);
            shareAddressRepo.save(shareAddress);
        }

        ShareSaveResDto shareSaveResDto = new ShareSaveResDto("공유코드 저장 성공");
        ResponseSuccessDto<ShareSaveResDto> res = responseUtil.successResponse(shareSaveResDto);
        return res;
    }

    // 공유코드에 저장된 도착주소
    @Transactional(readOnly = true)
    public ResponseSuccessDto<ShareSearchDestinationResDto> searchShareDestination(String code) {
        ShareEntity share = shareRepo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("잘못된 공유코드 입니다."));
        ShareSearchDestinationResDto shareSearchDestinationResDto = new ShareSearchDestinationResDto(
                share.getUuid(), share.getUserName(), share.getAddressName(), share.getPlaceName(), share.getRoadName(),
                share.getLat(), share.getLon()
        );
        ResponseSuccessDto<ShareSearchDestinationResDto> res = responseUtil.successResponse(shareSearchDestinationResDto);
        return res;
    }

    // 공유코드에 저장된 출발주소 리스트
    @Transactional(readOnly = true)
    public ResponseSuccessDto<List<ShareSearchStartResDto>> searchShareStartList(UUID shareUuid) {
        ShareEntity share = shareRepo.findById(shareUuid)
                .orElseThrow(() -> new EntityIsNullException("존재하지 않는 공유입니다."));
        List<ShareAddressEntity> startAddressList = shareAddressRepo.findByShareEntity(share);
        List<ShareSearchStartResDto> shareSearchStartResDtoList = startAddressList.stream().map(
                s -> new ShareSearchStartResDto(
                        s.getUserName(), s.getAddressName(), s.getPlaceName(), s.getRoadName(), s.getLat(), s.getLon()
                )
        ).collect(Collectors.toList());

        ResponseSuccessDto<List<ShareSearchStartResDto>> res = responseUtil.successResponse(shareSearchStartResDtoList);
        return res;
    }
}
