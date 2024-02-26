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
@Table(name = "expense")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name = "SEQUENCE3", sequenceName = "SEQUENCE3", allocationSize = 1)
    private Long expenceId;
    private String type;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;
    private Double amount;
    @ColumnDefault("1")
    private Integer active;

}
