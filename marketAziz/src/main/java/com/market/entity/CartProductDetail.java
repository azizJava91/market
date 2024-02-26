package com.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Table(name = "cart_product_details")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE13")
    @SequenceGenerator(name = "SEQUENCE13", sequenceName = "SEQUENCE13", allocationSize = 1)
    private Long id;
    private String name;
    private Double salePrice;
    private String brand;
    private String totalUnit;
    private Double totalPrice;
    private String category;
}
