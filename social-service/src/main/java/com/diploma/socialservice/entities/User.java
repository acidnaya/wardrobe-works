package com.diploma.socialservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false, insertable = true)
    private Long id;

    @Size(max = 30)
    @Column(name = "username", length = 30)
    private String username;

    @Size(max = 200)
    @Column(name = "bio", length = 200)
    private String bio;

    @Size(max = 200)
    @Column(name = "avatar", length = 200)
    private String avatar;
}