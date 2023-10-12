package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String name;
    @Column(columnDefinition="TEXT")
    private String address;
    @Column(columnDefinition="TEXT")
    private String phoneNumber;
    @Column(columnDefinition="TEXT")
    private String email;
    @Column(columnDefinition="TEXT")
    private String idCard;
    @Column(columnDefinition="TEXT")
    private String gender;
    private Date dateOfBirth;
    @Column(columnDefinition="TEXT")
    private String avatar;
}
