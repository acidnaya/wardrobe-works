package com.diploma.socialservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_images")
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Size(max = 200)
    @NotNull
    @Column(name = "image_path", nullable = false, length = 200)
    private String imagePath;

    @NotNull
    @Column(name = "\"position\"", nullable = false)
    private Short position;

    @NotNull
    @Column(name = "outfit_id", nullable = false)
    private Long outfitId;

}