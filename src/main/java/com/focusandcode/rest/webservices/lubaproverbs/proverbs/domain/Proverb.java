package com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Proverb {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String proverbId;
    @Size(min = 2, message = "originalText should have at least 2 characters")
    @NotEmpty(message = "originalText cannot be empty.")
    private String originalText;
    @Size(min = 2, message = "englishText should have at least 2 characters")
    @NotEmpty(message = "englishText cannot be empty.")
    private String englishText;
    @Size(min = 2, message = "englishMeaning should have at least 2 characters")
    @NotEmpty(message = "englishMeaning cannot be empty.")
    private String englishMeaning;
    private String frenchText;
    private String frenchMeaning;
    private Integer likes;
    private Integer shared;
    @ManyToMany(cascade = {CascadeType.PERSIST,  CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Category> categories;

    public Proverb() {
        // Needed for JPA
    }

    public Proverb( String proverbId,
             String originalText,
             String englishText,
             String englishMeaning,
             String frenchText,
             String frenchMeaning,
             Integer likes,
             Integer shared,
             Set<Category> categories) {
        this.proverbId = proverbId;
        this.originalText = originalText;
        this.englishText = englishText;
        this.englishMeaning = englishMeaning;
        this.frenchText = frenchText;
        this.frenchMeaning = frenchMeaning;
        this.likes = likes;
        this.shared = shared;
        this.categories = categories;
    }

    public String getProverbId() {
        return proverbId;
    }

    public void setProverbId(String id) {
        this.proverbId = id;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    public String getFrenchText() {
        return frenchText;
    }

    public void setFrenchText(String frenchText) {
        this.frenchText = frenchText;
    }

    public String getFrenchMeaning() {
        return frenchMeaning;
    }

    public void setFrenchMeaning(String frenchMeaning) {
        this.frenchMeaning = frenchMeaning;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getShared() {
        return shared;
    }

    public void setShared(Integer shared) {
        this.shared = shared;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Proverb{" +
                "proverbId='" + proverbId + '\'' +
                ", originalText='" + originalText + '\'' +
                ", englishText='" + englishText + '\'' +
                ", englishMeaning='" + englishMeaning + '\'' +
                ", frenchText='" + frenchText + '\'' +
                ", frenchMeaning='" + frenchMeaning + '\'' +
                ", likes=" + likes +
                ", shared=" + shared +
                ", categories=" + categories +
                '}';
    }
}
