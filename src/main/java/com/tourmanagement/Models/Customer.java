package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "id_card")
    private String idCard;

    @Column(nullable = false)
    private Boolean gender;

    @Column(
            nullable = false,
            name = "date_of_birth"
    )
    private Date dateOfBirth;

    @Column(
            nullable = true,
            columnDefinition="TEXT"
    )
    private String avatar;
}
