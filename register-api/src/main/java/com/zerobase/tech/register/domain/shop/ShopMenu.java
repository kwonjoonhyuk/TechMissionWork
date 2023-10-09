package com.zerobase.tech.register.domain.shop;

import com.zerobase.tech.register.domain.BaseEntity;
import com.zerobase.tech.register.domain.shop.add.AddShopMenuForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class ShopMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    @Audited
    private String menuName;
    @Audited
    private String menuDescription;
    @Audited
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public static ShopMenu of(Long sellerId, AddShopMenuForm form) {
        return ShopMenu.builder()
                .sellerId(sellerId)
                .menuName(form.getMenuName())
                .menuDescription(form.getMenuDescription())
                .price(form.getPrice())
                .build();
    }


}
