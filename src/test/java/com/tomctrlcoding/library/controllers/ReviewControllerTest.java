package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Review;
import com.tomctrlcoding.library.services.ReviewService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Test
    void testGetAllReviews() {
        List<Review> reviews = createTestList();
        when(reviewService.getAllReviews()).thenReturn(reviews);

        var result = reviewController.getAllReviews();

        assertEquals(Response.Status.OK, result.getStatusInfo());
        assertIterableEquals(reviews, (ArrayList<Review>) result.getEntity());

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void testGetReviewById() {
        Review review = createTestReview();
        when(reviewService.getReviewById(any(ObjectId.class))).thenReturn(review);

        var result = reviewController.getReviewById(new ObjectId());

        assertEquals(Response.Status.OK, result.getStatusInfo());
        assertEquals(review, result.getEntity());

        verify(reviewService, times(1)).getReviewById(any(ObjectId.class));
    }

    @Test
    void testGetReviewByIdNotFound() {

        when(reviewService.getReviewById(any(ObjectId.class))).thenThrow(NotFoundException.class);

        var result = reviewController.getReviewById(new ObjectId());

        assertEquals(Response.Status.NOT_FOUND, result.getStatusInfo());

        verify(reviewService, times(1)).getReviewById(any(ObjectId.class));
    }

    @Test
    void testGetReviewsByBookId() {
        List<Review> reviews = createTestList();
        when(reviewService.getReviewsByBookId(anyString())).thenReturn(reviews);

        var result = reviewController.getReviewByBookId("1");

        assertEquals(Response.Status.OK, result.getStatusInfo());
        assertEquals(reviews, result.getEntity());

        verify(reviewService, times(1)).getReviewsByBookId(anyString());
    }

    @Test
    void testGetReviewsByRating() {
        List<Review> reviews = createTestList();
        when(reviewService.getReviewsByRating(anyInt())).thenReturn(reviews);

        var result = reviewController.getReviewByRating(1);

        assertEquals(Response.Status.OK, result.getStatusInfo());
        assertEquals(reviews, result.getEntity());

        verify(reviewService, times(1)).getReviewsByRating(anyInt());
    }

    @Test
    void testCreateNewReview() {
        Review review = createTestReview();
        when(reviewService.createNewReview(any(Review.class))).thenReturn(review);

        try (var result = reviewController.createNewReview(review)) {

            assertEquals(Response.Status.CREATED, result.getStatusInfo());
            assertEquals(review, result.getEntity());
        }

        verify(reviewService, times(1)).createNewReview(any(Review.class));
    }

    @Test
    void testDeleteReview() {

        doNothing().when(reviewService).deleteReview(any(ObjectId.class));

        try (var result = reviewController.deleteReview(new ObjectId())) {

            assertEquals(Response.Status.NO_CONTENT, result.getStatusInfo());
        }
    }

    private List<Review> createTestList() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(createTestReview());

        return reviewList;
    }
    private Review createTestReview() {
        return new Review(new ObjectId(), new ObjectId().toString(), "This is a good review", 5, LocalDate.now());
    }
}
