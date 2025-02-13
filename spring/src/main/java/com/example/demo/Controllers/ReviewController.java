package com.example.demo.Controllers;


import com.example.demo.Entity.Product;
import com.example.demo.Entity.Review;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;

    public ReviewController(ReviewService reviewService, ProductService productService) {
        this.reviewService = reviewService;
        this.productService = productService;
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReview(@PathVariable Long productId){
        List<Review> reviews = reviewService.findReviewsByProductId(productId);
        Product product = productService.findProductById(productId);
        product.setReviewCount(reviews.size());
        productService.updateProduct(product);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Review> addReview(@PathVariable("productId")Long productId,@RequestBody Review review){
        Product product = productService.findProductById(productId);
        Review newReview = reviewService.addReview(product,review);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }


    @PutMapping("/product/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review){
        Review updateReview = reviewService.updateReview(review);

        System.out.println(review.toString());
        return new ResponseEntity<>(updateReview, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/product/delete/{reviewId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("reviewId")Long reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }









}