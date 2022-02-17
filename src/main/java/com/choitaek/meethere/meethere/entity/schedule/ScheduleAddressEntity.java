package com.choitaek.meethere.meethere.entity.schedule;

import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleAddressSaveReqDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "schedule_address")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleAddressEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "user_name", columnDefinition = "varchar(10)")
    private String userName;

    @Column(name = "address_name", columnDefinition = "varchar(50)")
    private String addressName;

    @Column(name = "place_name", columnDefinition = "varchar(50)")
    private String placeName;

    @Column(name = "road_name", columnDefinition = "varchar(50)")
    private String roadName;

    @Column(name = "lat", columnDefinition = "double")
    private double lat;

    @Column(name = "lon", columnDefinition = "double")
    private double lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_uuid", nullable = false)
    private ScheduleEntity scheduleEntity;

    public void createScheduleAddress(ScheduleAddressSaveReqDto scheduleAddressSaveReqDto, ScheduleEntity scheduleEntity) {
        this.userName = scheduleAddressSaveReqDto.getUserName();
        this.addressName = scheduleAddressSaveReqDto.getAddressName();
        this.placeName = scheduleAddressSaveReqDto.getPlaceName();
        this.roadName = scheduleAddressSaveReqDto.getRoadName();
        this.lat = scheduleAddressSaveReqDto.getLat();
        this.lon = scheduleAddressSaveReqDto.getLon();
        this.scheduleEntity = scheduleEntity;
    }
}
