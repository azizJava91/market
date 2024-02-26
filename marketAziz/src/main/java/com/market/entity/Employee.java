package com.market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "employee")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE2")
    @SequenceGenerator(name = "SEQUENCE2", sequenceName = "SEQUENCE2", allocationSize = 1)
    private Long employeeId;
    private String name;
    private String surname;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date dob;
    private String phone;
    private String pin;
    private String seria;
    private Double salary;
    private String position;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data_date;
    @ColumnDefault("1")
    private Integer active;
    @ManyToOne
    @JoinColumn(name = "department_Id")
    private Department department;
    @ColumnDefault("0")
    private Double balance;
}
