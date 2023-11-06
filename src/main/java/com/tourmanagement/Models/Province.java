package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "provinces")
public class Province {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String codename;
}
