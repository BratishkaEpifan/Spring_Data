package com.bobocode.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * TODO: you're job is to implement mapping for JPA entity {@link Movie}
 * - specify id
 * - configure id as auto-increment column
 * - explicitly specify each column name ("id", "name", "director", and "duration" accordingly)
 * - specify not null constraint for fields {@link Movie#name} and {@link Movie#director}
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "director", nullable = false)
    private String director;
    @Column(name = "duration")
    private Integer durationSeconds;
}