package com.choitaek.meethere.meethere.repository.jpa.schedule;

import com.choitaek.meethere.meethere.entity.schedule.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, UUID> {
    ScheduleEntity findOneByUuid(UUID uuid);

    Page<ScheduleEntity> findAll(Pageable pageable);

    Page<ScheduleEntity> findByMemberUuid(UUID memberUuid);
}
