package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "tour_guides")
@JsonIgnoreProperties("tours")
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

    @JsonManagedReference
    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER)
    private List<Tour> tours;

    @PreRemove
    public void preRemove() {
        for (Tour tour : tours) {
            tour.setGuide(null);
        }
    }
}
