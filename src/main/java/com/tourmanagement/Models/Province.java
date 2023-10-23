package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
