package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "tour_guides")
public class TourGuide {
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

    @JsonIgnore
    @OneToMany(mappedBy = "guide", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Tour> tours;

    @PreRemove
    public void preRemove() {
        for (Tour tour : tours) {
            tour.setGuide(null);
        }
    }
}
