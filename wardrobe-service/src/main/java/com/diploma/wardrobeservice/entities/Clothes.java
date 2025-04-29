package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wardrobe_id", nullable = false)
    private Wardrobe wardrobe;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "colour_id")
    private Colour colour;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false, insertable = false)
    private Boolean isDeleted = false;

    @Size(max = 200)
    @NotNull
    @Column(name = "image_path", nullable = false, length = 200, updatable = false)
    private String imagePath;

    @Column(name = "price")
    private Float price;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "number_of_wear", nullable = false, insertable = false)
    private Integer numberOfWear;

    @OneToMany(mappedBy = "cloth")
    private Set<OutfitClothes> outfitClothes = new LinkedHashSet<>();

    public String getTypeNameOrNull() {
        return Optional.ofNullable(type)
                .map(Type::getName)
                .orElse(null);
    }

    public String getColourNameOrNull() {
        return Optional.ofNullable(colour)
                .map(Colour::getName)
                .orElse(null);
    }

    public String getSeasonNameOrNull() {
        return Optional.ofNullable(season)
                .map(Season::getName)
                .orElse(null);
    }

    public String getBrandNameOrNull() {
        return Optional.ofNullable(brand)
                .map(Brand::getName)
                .orElse(null);
    }

}