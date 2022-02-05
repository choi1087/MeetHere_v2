package com.choitaek.meethere.meethere.repository.jpa.member;

import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepo extends JpaRepository<MemberEntity, UUID> {
    Page<MemberEntity> findAll(Pageable pageable);

    MemberEntity findOneByUuid(UUID uuid);

    MemberEntity findOneByEmail(String email);

    Page<MemberEntity> findByName(String name, Pageable pageable);

    MemberEntity findOneByNameAndPhone(String name, String phone);

    MemberEntity findOneByEmailAndNameAndPhone(String email, String name, String phone);

    Optional<MemberEntity> findByEmail(String email);
}
