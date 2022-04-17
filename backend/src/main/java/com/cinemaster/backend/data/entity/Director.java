package com.cinemaster.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "director")
public class Director {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @Size(min = 2, max = 32, message = "The name length must be between 6 and 32")
    @Pattern(regexp = "[a-zA-Z0-9 -\\.,]+", message = "The name must follow the pattern [a-zA-Z0-9 -.,]")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "directors", fetch = FetchType.EAGER)
    private List<Show> shows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
