package com.shop.onlineshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="role")
    private Integer role;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permission> permissions;

    @ManyToMany()
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude // to avoid infinite loop
    @JsonIgnore
    private List<Product> watchlist;
}
