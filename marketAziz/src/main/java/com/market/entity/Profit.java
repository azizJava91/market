package com.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "profit")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE11")
    @SequenceGenerator(name = "SEQUENCE11", sequenceName = "SEQUENCE11", allocationSize = 1)
    private Long profitId;
    private Double amount;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;
    @ColumnDefault("1")
    private Integer active;
    private Long cartId;
    private Long userId;

}
