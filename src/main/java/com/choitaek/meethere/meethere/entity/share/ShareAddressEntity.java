package com.choitaek.meethere.meethere.entity.share;

import com.choitaek.meethere.meethere.dto.share.ShareObjectDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "share_address")
@NoArgsConstructor
@AllArgsConstructor
public class ShareAddressEntity {

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
    @JoinColumn(name = "share_uuid", nullable = false)
    private ShareEntity shareEntity;

    public void createShareAddress(ShareObjectDto shareObjectDto) {
        this.userName = shareObjectDto.getUserName();
        this.addressName = shareObjectDto.getAddressName();
        this.placeName = shareObjectDto.getPlaceName();
        this.roadName = shareObjectDto.getRoadName();
        this.lat = shareObjectDto.getLat();
        this.lon = shareObjectDto.getLon();
    }
}
