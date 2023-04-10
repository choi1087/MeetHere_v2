package com.choitaek.meethere.meethere.repository.jpa.schedule;

import com.choitaek.meethere.meethere.entity.schedule.ScheduleAddressEntity;
import com.choitaek.meethere.meethere.entity.schedule.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleAddressRepo extends JpaRepository<ScheduleAddressEntity, UUID> {
    List<ScheduleAddressEntity> findByScheduleEntity(ScheduleEntity schedule);
}
