package com.choitaek.meethere.meethere.repository.jpa.member;

import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepo extends JpaRepository<MemberEntity, UUID> {
    Page<MemberEntity> findAll(Pageable pageable);

    MemberEntity findOneByUuid(UUID uuid);

    MemberEntity findOneByEmail(String email);
}
