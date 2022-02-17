package com.choitaek.meethere.meethere.entity.share;

import com.choitaek.meethere.meethere.dto.request.share.ShareSaveReqDto;
import com.choitaek.meethere.meethere.entity.TimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
@Table(name = "share")
@NoArgsConstructor
@AllArgsConstructor
public class ShareEntity extends TimeEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "user_name", columnDefinition = "varchar(10)")
    private String userName;

    @Column(name = "code", columnDefinition = "varchar(50)")
    private String code;

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

    public void createShare(ShareSaveReqDto shareSaveReqDto) {
        this.userName = shareSaveReqDto.getDestinationAddress().getUserName();
        this.addressName = shareSaveReqDto.getDestinationAddress().getAddressName();
        this.placeName = shareSaveReqDto.getDestinationAddress().getPlaceName();
        this.roadName = shareSaveReqDto.getDestinationAddress().getRoadName();
        this.lat = shareSaveReqDto.getDestinationAddress().getLat();
        this.lon = shareSaveReqDto.getDestinationAddress().getLon();
        this.code = makeRandomCode();
    }

    // 랜덤코드 생성
    private static String makeRandomCode() {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }
}
