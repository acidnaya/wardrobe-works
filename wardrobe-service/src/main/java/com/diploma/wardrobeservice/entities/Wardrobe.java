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
@Table(name = "wardrobes", schema = "wardrobe-service")
public class Wardrobe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false, insertable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "wardrobe")
    private Set<AccessPermission> accessPermissions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "wardrobe")
    private Set<Clothes> clothes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "wardrobe")
    private Set<Lookbook> lookbooks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "wardrobe")
    private Set<Outfit> outfits = new LinkedHashSet<>();

    @NotNull
    @ColumnDefault("false")
    @Column(name = "\"is_private\"", nullable = false)
    private Boolean isPrivate = false;

    @NotNull
    @Column(name = "creator_id", nullable = false)
    private String creatorId;
}