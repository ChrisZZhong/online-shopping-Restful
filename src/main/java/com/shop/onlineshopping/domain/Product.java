package com.shop.onlineshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer productId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="retail_price")
    private Double retailPrice;

    @Column(name="wholesale_price")
    private Double wholesalePrice;

    @ManyToMany(mappedBy = "watchlist")
    @ToString.Exclude
    @JsonIgnore
    private List<User> watchlistUsers;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @JsonIgnore
    private List<Item> items;
}
