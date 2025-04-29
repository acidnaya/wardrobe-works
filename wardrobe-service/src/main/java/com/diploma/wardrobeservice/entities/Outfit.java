package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outfits")
public class Outfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wardrobe_id", nullable = false)
    private Wardrobe wardrobe;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Size(max = 200)
    @Column(name = "image_path", length = 200)
    private String imagePath;

    @OneToMany(mappedBy = "outfit")
    private Set<CalendarEntry> calendarEntries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "outfit")
    private Set<OutfitClothes> outfitClothes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "outfit")
    private Set<LookbooksOutfit> lookbooksOutfits = new LinkedHashSet<>();

}