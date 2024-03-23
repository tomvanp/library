package com.tomctrlcoding.library.controllers;

import com.tomctrlcoding.library.model.Review;
import com.tomctrlcoding.library.services.ReviewServiceInterface;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/reviews")
@Singleton
public class ReviewController {

    @Inject
    private ReviewServiceInterface reviewService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReviews(){
        List<Review> reviews = reviewService.getAllReviews();

        return Response.ok().entity(reviews).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewById(@PathParam("id") ObjectId id){
        try {
            Review review = reviewService.getReviewById(id);
            return Response.ok().entity(review).build();
        } catch (NotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewByBookId(@QueryParam("bookId") String id){
            List<Review> review = reviewService.getReviewsByBookId(id);
            return Response.ok().entity(review).build();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewByRating(@QueryParam("rating") int rating){
        List<Review> review = reviewService.getReviewsByRating(rating);
        return Response.ok().entity(review).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewReview(Review review) {
        Review newReview = reviewService.createNewReview(review);
        return Response.status(Response.Status.CREATED).entity(newReview).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReview(@PathParam("id") ObjectId id) {
        reviewService.deleteReview(id);
        return Response.noContent().build();
    }
}
