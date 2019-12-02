package com.vaadin.example;

import com.google.gson.JsonArray;
import com.vaadin.example.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.awt.print.Book;
import java.util.List;

public interface VehicleManagerRepository  {
    @GET("vehicles")
    Call<JsonArray> getAllVehicles();

    @POST("booking")
    Call<BookingStatus> postBookingDetails(@Body Booking booking);

    @GET("booking")
    Call<List<Booking>> getBookingDetails();
}
