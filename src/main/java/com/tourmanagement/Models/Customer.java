package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(
            nullable = true,
            columnDefinition="TEXT"
    )
    private String avatar;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomerDiscount> customerDiscounts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookedTour> bookedTours;
}
