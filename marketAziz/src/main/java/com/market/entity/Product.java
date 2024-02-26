package com.market.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "product")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE4")
    @SequenceGenerator(name = "SEQUENCE4", sequenceName = "SEQUENCE4", allocationSize = 1)
    private Long productId;
    private String name;
    private Date expirationDate;
    private String measureUnit;
    private Double salePrice;
    private Double purchasePrice;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;
    @ColumnDefault("1")
    private Integer active;
    private Date manufacturedDate;
    private String brand;
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;




}
