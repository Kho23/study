package com.green.blue.red.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = "owner")
@Table(name = "tbl_cart", indexes = {@Index(name = "idx_cart",columnList = "member_owner")})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @OneToOne
    @JoinColumn(name="member_owner")
    private Member owner;

}
