package com.tomctrlcoding.library.repositories;

import com.tomctrlcoding.library.model.Review;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;
import org.bson.types.ObjectId;

import java.util.stream.Stream;

@Repository
public interface ReviewRepository extends BasicRepository<Review, ObjectId> {

    Stream<Review> findByBookId(String bookId);

    Stream<Review> findByRating(int rating);
}
