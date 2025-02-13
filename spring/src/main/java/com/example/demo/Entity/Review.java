package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;


    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id") // Assuming "id" is the primary key of the Product entity
    private Product product;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id") // Assuming "id" is the primary key of the Author entity
    private Users author;
    private String content;
    private Integer rating;
    private Integer likes;
    private Integer dislikes;

    public Review() {
    this.rating = 5;
    this.likes = 0;
    this.dislikes = 0;
    }


    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
