package com.vaadin.example;

import com.vaadin.example.model.Car;
import com.vaadin.example.model.Motorbike;
import com.vaadin.example.model.Vehicle;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface VehicleManagerRepository  {
    @GET("cars")
    Call<List<Car>> getAllCars();

    @GET("motorbikes")
    Call<List<Motorbike>> getAllMotorbikes();
}
