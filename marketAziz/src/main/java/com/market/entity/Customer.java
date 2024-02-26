package com.market.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@Data
@Entity
@Table(name = "customer")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE10")
    @SequenceGenerator(name = "SEQUENCE10", sequenceName = "SEQUENCE10", allocationSize = 1)
    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Double balance;

    @ColumnDefault(value = "1")
    private Integer active;
    @OneToOne
    private User user;


}
