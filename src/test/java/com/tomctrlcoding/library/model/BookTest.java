package com.tomctrlcoding.library.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    final static Logger logger = LoggerFactory.getLogger("BookTest");

    static Validator validator;

    @BeforeAll
    static void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            logger.error("Exception with validationFactory in BookTest: {}", e.getMessage());
        }
    }

    @Test
    public void testValidBook() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-5521-3325-8");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.info("testValidBook : Found violations: {}", violations);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidBookNullId() {
        Book book = new Book(null, "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-5521-3325-8");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.info("testValidBookNullId : Found violations: {}", violations);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidBookEmptyTitle() {
        Book book = new Book(new ObjectId(), "", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-5521-3325-8");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        logger.info("testInvalidBookEmptyTitle : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }


    @Test
    public void testInvalidBookEmptyAuthor() {
        Book book = new Book(new ObjectId(), "Test Title", "", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-5521-3325-8");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    public void testInvalidBookNullGenre() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", null, "Fake Publish", Year.of(2024), "978-0-5521-3325-8");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidBookEmptyPublisher() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "", Year.of(2024), "978-0-5521-3325-8");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    public void testInvalidBookISBNWrongCheckNumber() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-5521-3325-1");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.info("Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testInValidBookFutureDate() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2025), "978-0-5521-3325-8");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.info("Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testInvalidBookISBNWrongSize() {
        Book book = new Book(new ObjectId(), "Test Title", "John Doe", Genre.MYSTERY, "Fake Publish", Year.of(2024), "978-0-551-335-8");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        logger.info("Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

}
