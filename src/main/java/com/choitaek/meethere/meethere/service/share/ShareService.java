package com.choitaek.meethere.meethere.service.share;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.share.ShareSaveReqDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSaveResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchDestinationResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchStartResDto;
import com.choitaek.meethere.meethere.dto.share.ShareObjectDto;
import com.choitaek.meethere.meethere.entity.share.ShareAddressEntity;
import com.choitaek.meethere.meethere.entity.share.ShareEntity;
import com.choitaek.meethere.meethere.repository.jpa.share.ShareAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.share.ShareRepo;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ShareService {

    private final ResponseUtil responseUtil;

    private final ShareRepo shareRepo;
    private final ShareAddressRepo shareAddressRepo;

    // 공유코드 저장
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
        ShareEntity share = shareRepo.findOneByCode(code);
        if (share == null) {
            try {
                throw new Exception("잘못된 공유코드입니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        ShareEntity share = shareRepo.findOneByUuid(shareUuid);
        Page<ShareAddressEntity> startAddressPage = shareAddressRepo.findByShareEntity(share, PageRequest.of(0, 20));
        Page<ShareSearchStartResDto> toMap = startAddressPage.map(s -> new ShareSearchStartResDto(
                        s.getUserName(), s.getAddressName(), s.getPlaceName(), s.getRoadName(), s.getLat(), s.getLat()
                )
        );
        List<ShareSearchStartResDto> shareSearchStartResDtoList = toMap.getContent();
        ResponseSuccessDto<List<ShareSearchStartResDto>> res = responseUtil.successResponse(shareSearchStartResDtoList);
        return res;
    }

    // 공유코드 삭제
}
