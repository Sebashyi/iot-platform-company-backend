package com.m3verificaciones.appweb.company.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.m3verificaciones.appweb.company.util.UniqueIdGenerator;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(length = 10, unique = true, nullable = false, name = "unique_key")
    private String uniqueKey;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @Column(unique = true, length = 13, nullable = false)
    @NotBlank
    private String ruc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Transient
    @JsonIgnore
    private UniqueIdGenerator key;

    @PrePersist
    protected void onCreate() {
        this.uniqueKey = UniqueIdGenerator.generateUniqueKey();
    }
}
