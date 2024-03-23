package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Review;
import com.tomctrlcoding.library.repositories.ReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class ReviewService implements ReviewServiceInterface{

    @Inject
    private ReviewRepository reviewRepository;
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll().toList();
    }

    @Override
    public Review getReviewById(ObjectId id) {
        return reviewRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Review> getReviewsByBookId(String bookId) {
        return reviewRepository.findByBookId(bookId).toList();
    }

    @Override
    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating).toList();
    }

    @Override
    public Review createNewReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(ObjectId id) {
        reviewRepository.deleteById(id);
    }
}
