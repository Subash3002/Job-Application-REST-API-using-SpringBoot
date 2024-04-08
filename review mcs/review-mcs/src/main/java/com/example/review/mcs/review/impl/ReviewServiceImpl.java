package com.example.review.mcs.review.impl;


import com.example.review.mcs.review.Review;
import com.example.review.mcs.review.ReviewRepository;
import com.example.review.mcs.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId,Review review) {
        if(companyId!=null && review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReviewById(Long reviewId, Review updtReview) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            review.setTitle(updtReview.getTitle());
            review.setDescription(updtReview.getDescription());
            review.setRating(updtReview.getRating());
            review.setCompanyId(updtReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteReviewById(Long reviewId) {
        Review review=reviewRepository.findById(reviewId).orElse(null);
        if(review!=null){
            reviewRepository.delete(review);
            return true;
        }else{
            return false;
        }
    }
}
