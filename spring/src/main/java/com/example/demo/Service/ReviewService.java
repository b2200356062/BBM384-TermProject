package com.example.demo.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Review;
import com.example.demo.UserRepo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public Review addReview(Product product, Review review){
        review.setProduct(product);
        return reviewRepo.save(review);
    }


    public List<Review> findReviewsByProductId(Long productId){
        return reviewRepo.findReviewByProductId(productId);
    }

    public Review updateReview(Review review){
        return reviewRepo.save(review);
    }

    public void deleteReview(Long id){
        reviewRepo.deleteReviewByReviewId(id);
    }
}