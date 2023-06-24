package com.shop.onlineshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Double wholesalePrice;

    @ManyToOne
    @JoinColumn(name="order_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;
}
