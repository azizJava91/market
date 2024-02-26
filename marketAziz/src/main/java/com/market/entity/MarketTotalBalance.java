package com.market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Table(name = "market_total_balance")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketTotalBalance {
    @Id
    private Long id;
    private Double totalBalance;
    @ColumnDefault("1")
    private Integer active;
}
