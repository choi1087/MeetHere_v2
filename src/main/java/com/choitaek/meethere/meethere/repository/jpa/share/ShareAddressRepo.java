package com.choitaek.meethere.meethere.repository.jpa.share;

import com.choitaek.meethere.meethere.entity.share.ShareAddressEntity;
import com.choitaek.meethere.meethere.entity.share.ShareEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShareAddressRepo extends JpaRepository<ShareAddressEntity, UUID> {
    List<ShareAddressEntity> findByShareEntity(ShareEntity shareEntity);
}
