package com.jfsd.exit_portal_backend.Model;

import jakarta.persistence.*;

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
