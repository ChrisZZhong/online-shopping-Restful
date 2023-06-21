package com.shop.onlineshopping.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import org.springframework.stereotype.Component;


@Component
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="permission_id")
    private Integer permissionId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="value")
    private String value;

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    @ToString.Exclude // to avoid infinite loop
    @JsonIgnore
    private User user;

}
