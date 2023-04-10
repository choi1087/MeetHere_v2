package com.choitaek.meethere.meethere.repository.jpa.schedule;

import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.entity.schedule.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, UUID> {
    Page<ScheduleEntity> findAll(Pageable pageable);

    List<ScheduleEntity> findByMemberEntity(MemberEntity memberEntity);
}
