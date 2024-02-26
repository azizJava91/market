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
import java.util.List;

@Data
@Entity
@Table(name = "usersss")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE6")
    @SequenceGenerator(name = "SEQUENCE6", sequenceName = "SEQUENCE6", allocationSize = 1)
    private Long userId;
    private String username;
    private String password;
    private String fullName;
    @ColumnDefault(value = "customer")
    private String role;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data_date;
    @ColumnDefault(value = "1")
    private Integer active;

}
