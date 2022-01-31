package com.choitaek.meethere.meethere.repository.jpa.friend;

import com.choitaek.meethere.meethere.entity.friend.FriendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRepo extends JpaRepository<FriendEntity, UUID> {
    Page<FriendEntity> findByMemberUuid(UUID uuid);

    FriendEntity findOneByEmail(String email);
}
