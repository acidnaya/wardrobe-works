package com.diploma.wardrobeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "calendar", schema = "wardrobe")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_private", nullable = false, insertable = false)
    private Boolean isPrivate = false;

    @OneToMany(mappedBy = "calendarId")
    private Set<CalendarEntry> calendarEntries = new LinkedHashSet<>();

}