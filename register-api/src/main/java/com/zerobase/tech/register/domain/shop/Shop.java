package com.zerobase.tech.register.domain.shop;

import com.zerobase.tech.register.domain.BaseEntity;
import com.zerobase.tech.register.domain.shop.add.AddShopForm;
import com.zerobase.tech.register.kakaoapi.KakaoAddress;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class Shop extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long sellerId;

    private String shopName;

    private String shopDescription;

    private String address;
    private String roadAddress;


    private double longitude;
    private double latitude;
    private double stars;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private List<ShopMenu> shopMenus = new ArrayList<>();


    public static Shop of(Long sellerId, AddShopForm form) {
        KakaoAddress kakaoAddress = new KakaoAddress();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList = kakaoAddress.getLocation(form.getAddress());

        double longitude = Double.parseDouble(arrayList.get(2));
        double latitude = Double.parseDouble(arrayList.get(3));

        System.out.println("longitude 값 : " + longitude);
        System.out.println("latitude 값 : " + latitude);

        if (!arrayList.isEmpty()) {
            return Shop.builder()
                    .sellerId(sellerId)
                    .shopName(form.getShopName())
                    .address(arrayList.get(0))
                    .roadAddress(arrayList.get(1))
                    .longitude(longitude)
                    .latitude(latitude)
                    .shopMenus(form.getItems().stream()
                            .map(shopMenuForm -> ShopMenu.of(sellerId, shopMenuForm)).collect(Collectors.toList()))
                    .shopDescription(form.getShopDescription())
                    .stars(0.0)
                    .build();
        } else {
            return null;
        }

    }


}
