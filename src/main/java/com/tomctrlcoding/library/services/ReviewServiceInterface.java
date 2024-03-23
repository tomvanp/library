package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Review;
import org.bson.types.ObjectId;

import java.util.List;

public interface ReviewServiceInterface {

    List<Review> getAllReviews();

    Review getReviewById(ObjectId id);

    List<Review> getReviewsByBookId(String bookId);

    List<Review> getReviewsByRating(int rating);

    Review createNewReview(Review review);

    void deleteReview(ObjectId id);
}
