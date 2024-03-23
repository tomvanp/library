package com.tomctrlcoding.library.services;

import com.tomctrlcoding.library.model.Review;
import com.tomctrlcoding.library.repositories.ReviewRepository;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository repository;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testGetAllReviews() {
        List<Review> reviewList = createTestList();

        when(repository.findAll()).thenReturn(reviewList.stream());

        var result = reviewService.getAllReviews();


        assertIterableEquals(reviewList, result);
        verify(repository, times(1)).findAll();

    }

    @Test
    void testGetReviewById() {
        var review = createTestReview();

        when(repository.findById(any(ObjectId.class))).thenReturn(Optional.of(review));

        var result = reviewService.getReviewById(review.id());

        assertEquals(review, result);
        verify(repository, times(1)).findById(any(ObjectId.class));
    }

    @Test
    void testGetReviewByIdNotFound() {

        when(repository.findById(any(ObjectId.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            reviewService.getReviewById(new ObjectId());
        }, "NotFoundException was expected");

        verify(repository, times(1)).findById(any(ObjectId.class));
    }

    @Test
    void testGetReviewByBookId() {
        List<Review> reviewList = createTestList();

        when(repository.findByBookId(anyString())).thenReturn(reviewList.stream());

        var result = reviewService.getReviewsByBookId("1");

        assertIterableEquals(reviewList, result);
        verify(repository, times(1)).findByBookId(anyString());
    }

    @Test
    void testGetReviewByRating() {
        List<Review> reviewList = createTestList();

        when(repository.findByRating(anyInt())).thenReturn(reviewList.stream());

        var result = reviewService.getReviewsByRating(5);

        assertIterableEquals(reviewList, result);
        verify(repository, times(1)).findByRating(anyInt());
    }

    @Test
    void testCreateNewReview() {
        var review = createTestReview();

        when(repository.save(any(Review.class))).thenReturn(review);

        var result = reviewService.createNewReview(review);

        assertEquals(review, result);
        verify(repository, times(1)).save(any(Review.class));
    }

    @Test
    void testDeleteReview(){
        doNothing().when(repository).deleteById(any(ObjectId.class));

        reviewService.deleteReview(new ObjectId());

        verify(repository, times(1)).deleteById(any(ObjectId.class));
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
