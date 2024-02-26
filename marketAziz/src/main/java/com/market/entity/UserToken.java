package com.market.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "user_token")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE7")
    @SequenceGenerator(name = "SEQUENCE7", sequenceName = "SEQUENCE7", allocationSize = 1)
    private Long id;
    private String token;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data_date;
    @ColumnDefault(value = "1")
    private Integer active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}