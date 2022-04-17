package com.cinemaster.backend.data.dto;

import com.cinemaster.backend.data.entity.Show;

import java.time.LocalDate;
import java.util.List;

public class ShowDto {

    private Long id;

    private String name;

    private String photoUrl;

    private String language;

    private String productionLocation;

    private String description;

    private Boolean comingSoon;

    private Boolean highlighted;

    private Long length;

    private LocalDate releaseDate;

    private String urlHighlighted;

    private String urlTrailer;

    private Show.Rating rating;

    private List<CategoryDto> categories;

    private List<DirectorDto> directors;

    private List<ActorDto> actors;

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

    public void setProductionLocation(String productionLocation) {
        this.productionLocation = productionLocation;
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

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<DirectorDto> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorDto> directors) {
        this.directors = directors;
    }

    public List<ActorDto> getActors() {
        return actors;
    }

    public void setActors(List<ActorDto> actors) {
        this.actors = actors;
    }

    public String getUrlHighlighted() {
        return urlHighlighted;
    }

    public void setUrlHighlighted(String urlHighlighted) {
        this.urlHighlighted = urlHighlighted;
    }

    public Show.Rating getRating() {
        return rating;
    }

    public void setRating(Show.Rating rating) {
        this.rating = rating;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }
}
