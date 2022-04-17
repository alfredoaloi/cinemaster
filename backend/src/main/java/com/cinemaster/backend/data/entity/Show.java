package com.cinemaster.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "show_table")
public class Show {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 100, message = "The name length must be between 1 and 100")
    @Pattern(regexp = "[\\w -\\.,:;\\+_\\(\\)\\[\\]\\{\\}]+", message = "The name must follow the pattern [a-zA-Z0-9 -.,:;+()[]{}]")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 7, max = 255, message = "The url length must be between 7 and 255")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)", message = "The url is not valid")
    @Column(name = "photo_url")
    private String photoUrl;

    @Size(min = 1, max = 50, message = "The language length must be between 7 and 255")
    @Pattern(regexp = "[\\w ]+", message = "The language must be composed only by letters, numbers and spaces")
    @Column(name = "language")
    private String language;

    @Size(min = 1, max = 50, message = "The location length must be between 7 and 255")
    @Pattern(regexp = "[\\w ]+", message = "The location must be composed only by letters, numbers and spaces")
    @Column(name = "production_location")
    private String productionLocation;

    @Size(min = 1, max = 100, message = "The description length must be between 1 and 1000")
    @Pattern(regexp = "[\\w -\\.,:;\\+_\\(\\)\\[\\]\\{\\}]+", message = "The description must follow the pattern [a-zA-Z0-9 -.,:;+()[]{}]")
    @Column(name = "description")
    private String description;

    @Column(name = "coming_soon", nullable = false)
    private Boolean comingSoon;

    @Column(name = "highlighted", nullable = false)
    private Boolean highlighted;

    @Column(name = "length")
    private Long length;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "url_highlighted")
    private String urlHighlighted;

    @Column(name = "url_trailer")
    private String urlTrailer;

    @Column(name = "rating")
    @Enumerated(value = EnumType.STRING)
    private Rating rating;

    @OneToMany(mappedBy = "show", fetch = FetchType.EAGER)
    private List<Event> events;

    @ManyToMany
    @JoinTable(
            name = "show_categories",
            joinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "show_directors",
            joinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
    )
    private List<Director> directors;

    @ManyToMany
    @JoinTable(
            name = "show_actors",
            joinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id")
    )
    private List<Actor> actors;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(String production_location) {
        this.productionLocation = production_location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComingSoon() {
        return comingSoon;
    }

    public void setComingSoon(Boolean comingSoon) {
        this.comingSoon = comingSoon;
    }

    public Boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getUrlHighlighted() {
        return urlHighlighted;
    }

    public void setUrlHighlighted(String urlHighlighted) {
        this.urlHighlighted = urlHighlighted;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    public enum Rating {PT, BA, VM14, VM18}
}
