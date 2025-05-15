package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "outfit_clothes")
public class OutfitClothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outfit_id", nullable = false)
    private Outfit outfit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cloth_id", nullable = false)
    private Clothes cloth;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "x", nullable = false)
    private Float x;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "y", nullable = false)
    private Float y;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "z_index", nullable = false)
    private Short zIndex;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "rotation", nullable = false)
    private Short rotation;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "scale", nullable = false)
    private Float scale;

}