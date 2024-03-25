package com.tomctrlcoding.library.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShelveTest {

    final static Logger logger = LoggerFactory.getLogger("ShelveTest");
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
    void testValidShelve() {
        var shelve = new Shelve("Test");

        Set<ConstraintViolation<Shelve>> violations = validator.validate(shelve);
        logger.info("testValidBook : Found violations: {}", violations);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInValidShelveEmptyName() {
        var shelve = new Shelve("");

        Set<ConstraintViolation<Shelve>> violations = validator.validate(shelve);
        logger.info("testValidBook : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void testInValidShelveBlankName() {
        var shelve = new Shelve(" ");

        Set<ConstraintViolation<Shelve>> violations = validator.validate(shelve);
        logger.info("testValidBook : Found violations: {}", violations);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void testAddBookId() {
        var shelve = new Shelve("Test");
        shelve.addBookId("1");

        assertEquals(1, shelve.getBookIds().size());
    }

    @Test
    void testAddBookIdNullId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(NullPointerException.class, () -> {
            shelve.addBookId(null);
        }, "NullPointerException was expected");
    }

    @Test
    void testAddBookIdEmptyId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(AssertionError.class, () -> {
            shelve.addBookId("");
        }, "AssertionError was expected");
    }

    @Test
    void testAddBookIdBlankId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(AssertionError.class, () -> {
            shelve.addBookId(" ");
        }, "AssertionError was expected");
    }

    @Test
    void testRemoveBookId() {
        var shelve = new Shelve("Test");
        shelve.addBookId("1");

        shelve.removeBookId("1");

        assertEquals(0, shelve.getBookIds().size());
    }

    @Test
    void testRemoveBookIdNullId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(NullPointerException.class, () -> {
            shelve.removeBookId(null);
        }, "NullPointerException was expected");
    }

    @Test
    void testRemoveBookIdEmptyId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(AssertionError.class, () -> {
            shelve.removeBookId("");
        }, "AssertionError was expected");
    }

    @Test
    void testRemoveBookIdBlankId() {
        var shelve = new Shelve("Test");

        Assertions.assertThrows(AssertionError.class, () -> {
            shelve.removeBookId(" ");
        }, "AssertionError was expected");
    }
}
