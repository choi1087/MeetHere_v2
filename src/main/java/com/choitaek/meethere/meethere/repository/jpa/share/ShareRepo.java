package com.choitaek.meethere.meethere.repository.jpa.share;

import com.choitaek.meethere.meethere.entity.share.ShareEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShareRepo extends JpaRepository<ShareEntity, UUID> {
    Page<ShareEntity> findAll(Pageable pageable);

    ShareEntity findOneByCode(String code);

    ShareEntity findOneByUuid(UUID uuid);
}
