package com.tourmanagement.Model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class TourGuide {
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
}
