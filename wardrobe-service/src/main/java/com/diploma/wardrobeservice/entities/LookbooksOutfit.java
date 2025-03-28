package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lookbooks_outfits", schema = "wardrobe-service")
public class LookbooksOutfit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lookbooks_outfits_id_gen")
    @SequenceGenerator(name = "lookbooks_outfits_id_gen", sequenceName = "lookbooks_outfits_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lookbook_id", nullable = false)
    private Lookbook lookbook;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outfit_id", nullable = false)
    private Outfit outfit;

}