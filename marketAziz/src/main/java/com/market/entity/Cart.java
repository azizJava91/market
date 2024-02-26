package com.market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "cart")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE12")
    @SequenceGenerator(name = "SEQUENCE12", sequenceName = "SEQUENCE12", allocationSize = 1)
    private Long cartId;
    private Double totalSalePrice;
    private Double totalPurchasePrice;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;
    @ColumnDefault("1")
    private Integer active;

    private Double profit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany
    List<CartProductDetail> cartProductDetails;
}
