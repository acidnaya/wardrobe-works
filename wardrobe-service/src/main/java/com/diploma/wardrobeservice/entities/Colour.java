package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "colours")
public class Colour {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "colourcode", nullable = false, length = 20)
    private String colourcode;

    @OneToMany(mappedBy = "colour")
    private Set<Clothes> clothes = new LinkedHashSet<>();

}