package com.tomctrlcoding.library.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {

    final static Logger logger = LoggerFactory.getLogger("ReviewTest");

    static Validator validator;

    @BeforeAll
    static void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            logger.error("Exception with validationFactory in ReviewTest: {}", e.getMessage());
        }
    }

    @Test
    public void testValidReviewNullDate(){
        Review review = new Review("This is a review", 3, null);

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testValidReviewNullDate : Found violations: {}", violations);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidReview(){
        Review review = new Review("This is a review", 3, LocalDate.now());

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testValidReviewNullDate : Found violations: {}", violations);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInValidReviewEmptyReview(){
        Review review = new Review("", 3, LocalDate.now());

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testInValidReviewEmptyReview : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    public void testInValidReviewExceedsCharacterLimit(){

        String reviewText300Characters = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed dignissim ante lacus, et fermentum magna efficitur a. Vivamus at ipsum ac mi interdum finibus.
                Donec fringilla urna ut varius ultrices. In est lectus, aliquet sed sollicitudin ac, sagittis sit amet libero. In nibh turpis, elementum nec vestibulum nec, pharetra sit amet urna.
                Donec fermentum lectus quis sodales pretium. Proin quam erat, pharetra non lectus quis, pretium viverra erat. Cras et dapibus elit. Sed a volutpat ante, eu molestie mi.
                Nulla fermentum neque eu ultricies iaculis. Nullam feugiat erat eget sapien blandit gravida.

                Cras at eros purus. Duis at vulputate risus. Suspendisse potenti. Maecenas bibendum neque a diam sollicitudin aliquet. In hac habitasse platea dictumst. Vivamus ipsum massa, porta in velit sed,
                tincidunt dictum purus. Vivamus at feugiat magna. Curabitur varius lectus tortor, euismod sollicitudin diam efficitur vitae. Praesent ut metus sed dolor molestie ullamcorper nec sed velit.

                Pellentesque mollis eros feugiat lacinia luctus. Proin et vehicula augue, vitae tempus velit. Vestibulum dapibus imperdiet magna ac sollicitudin. Pellentesque et malesuada ex, eget convallis urna.
                Vivamus pellentesque nisi vitae felis sagittis, et euismod nulla lacinia. Nullam egestas pretium massa id posuere. Morbi condimentum quam ligula. Mauris cursus nibh nec laoreet suscipit.
                In hac habitasse platea dictumst. Donec pretium sem et ipsum tempor sagittis. Aliquam ac elementum ex, sed blandit nulla. Vivamus eget mollis orci. Proin molestie eu nisl ut auctor.
                Praesent commodo blandit luctus. Nunc non quam at velit porta auctor quis ut neque.

                Proin eget mauris a tellus maximus sagittis eget nec risus. Suspendisse tristique tincidunt ultrices. Morbi blandit aliquet odio. Pellentesque fringilla lectus dolor,
                quis varius elit rhoncus non. Nam sodales, enim eu convallis sagittis, tellus lacus tempus quam, et posuere lectus ex in mauris. Aenean hendrerit, magna sit amet consequat suscipit,
                nisl nulla consectetur neque, lobortis laoreet lorem odio.\s""";

        Review review = new Review(reviewText300Characters, 3, LocalDate.now());

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testInValidReviewEmptyReview : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testInValidReviewWrongRating(){
        Review review = new Review("This is a review", 6, LocalDate.now());

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testInValidReviewEmptyReview : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testInValidReviewNegativeRating(){
        Review review = new Review("This is a review", -1, LocalDate.now());

        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        logger.info("testInValidReviewEmptyReview : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}
