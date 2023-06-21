package com.shop.onlineshopping.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Integer orderId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="date_placed")
    private Date datePlaced;

    @Column(name="order_status")
    private String orderStatus;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<Item> items;
}
