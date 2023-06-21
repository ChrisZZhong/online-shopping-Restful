package com.shop.onlineshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "order_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Integer itemId;

    @Column(name="order_id")
    private Integer orderId;

    @Column(name="product_id")
    private Integer productId;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="purchased_price")
    private Double purchasedPrice;

    @Column(name="wholesale_price")
    private Double WholesalePrice;

    @ManyToOne
    @JoinColumn(name="order_id", insertable = false, updatable = false)
    @JsonIgnore
    private Order order;
}
