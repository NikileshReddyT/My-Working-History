package com.jfsd.exit_portal_backend.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    private String categoryName;
    private int minCourses;
    private double minCredits;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Courses> courses = new ArrayList<>();

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMinCourses() {
        return minCourses;
    }

    public void setMinCourses(int minCourses) {
        this.minCourses = minCourses;
    }

    public double getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(double minCredits) {
        this.minCredits = minCredits;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return categoryID == that.categoryID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID);
    }
}
