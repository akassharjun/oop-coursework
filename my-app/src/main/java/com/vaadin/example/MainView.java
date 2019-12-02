package com.vaadin.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vaadin.example.manager.DatabaseManager;
import com.vaadin.example.model.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Route
public class MainView extends VerticalLayout {
    private List<Vehicle> vehicleList = new ArrayList<>();
    private List<Booking> bookingList = new ArrayList<>();
    private Vehicle selectedVehicle = null;
    private UI ui;


    private void fetchAllVehicles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:7000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VehicleManagerRepository service = retrofit.create(VehicleManagerRepository.class);

        service.getAllVehicles().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                assert response.body() != null;
                JsonArray jsonArray = response.body();

                jsonArray.forEach(json -> {
                    Vehicle vehicle;
                    if (json.getAsJsonObject().has("numberOfDoors")) {
                        vehicle = new Gson().fromJson(json.getAsJsonObject(), Car.class);
                    } else {
                        vehicle = new Gson().fromJson(json.getAsJsonObject(), Motorbike.class);
                    }

                    vehicleList.add(vehicle);
                });
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });

        service.getBookingDetails().enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                assert response.body() != null;
                bookingList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }


    private void placeBookingOrder(Booking booking) {

    }


    public MainView() {
        fetchAllVehicles();
        H1 headingTitle = new H1("Westminster Vehicle Rental Manager");
        headingTitle.getElement().getStyle().set("margin", "0px");
        ui = UI.getCurrent();
        Grid<Vehicle> grid = new Grid<>(Vehicle.class);
        grid.setColumns("make", "plateNumber", "transmission", "rate");
        grid.setColumnReorderingAllowed(true);
        grid.setItemDetailsRenderer(new ComponentRenderer<>(vehicle -> {
            VerticalLayout layout = new VerticalLayout();
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                VerticalLayout verticalLayoutOne = new VerticalLayout(new H6("Doors"), new Label("" + car.getNumberOfDoors()));
                Icon yes = new Icon(VaadinIcon.CHECK);
                yes.setColor("lightgreen");

                Icon no = new Icon(VaadinIcon.CLOSE);
                no.setColor("lightred");

                VerticalLayout verticalLayoutTwo = new VerticalLayout(new H6("SunRoof"), (car.isHasSunRoof() ? yes : no));

                HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutOne, verticalLayoutTwo);

                layout.add(horizontalLayout);
            } else {
                Motorbike motorbike = (Motorbike) vehicle;
                String standType = motorbike.getStandType().toString().substring(0, 1).toUpperCase() + motorbike.getStandType().toString().substring(1).toLowerCase();
                VerticalLayout verticalLayoutOne = new VerticalLayout(new H6("Stand Type"), new Label("" + standType));
                Icon yes = new Icon(VaadinIcon.CHECK);
                yes.setColor("lightgreen");

                Icon no = new Icon(VaadinIcon.CLOSE);
                no.setColor("lightred");

                VerticalLayout verticalLayoutTwo = new VerticalLayout(new H6("PEDALS"), (motorbike.isHasPedals() ? yes : no));

                HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutOne, verticalLayoutTwo);

                horizontalLayout.getElement().getStyle().set("margin", "0").set("padding", "0");
                layout.add(horizontalLayout);
            }

            if (!bookingList.isEmpty()) {
                H6 h6 = new H6("Previous Bookings");
                layout.add(h6);
                boolean noBookings = true;
                for (Booking booking : bookingList) {
                    if (booking.getPlateNumber().equals(vehicle.getPlateNumber())) {
                        Icon date = new Icon(VaadinIcon.DATABASE);

                        Label dateRange = new Label(booking.getSchedule().getPickUpDate().toString() + " - " + booking.getSchedule().getDropOffDate().toString());

                        HorizontalLayout bookingDetailsContainer = new HorizontalLayout(date, dateRange);
                        bookingDetailsContainer.getElement().getStyle().set("margin", "0").set("padding", "0");

                        noBookings = false;
                        layout.add(dateRange);
                    }
                }

                if (noBookings) {
                    layout.add(new Label("This Vehicle hasn't been booked before"));
                }

            }


            return layout;
        }));

        TextField searchBox = new TextField("Search");

        searchBox.setPlaceholder("Search");

        Button searchButton = new Button("Search", event -> {
            List<Vehicle> newVehicleList = new ArrayList<>();
            for (Vehicle vehicle : vehicleList) {
                if (vehicle.getPlateNumber().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
                    newVehicleList.add(vehicle);
                }
                if (vehicle.getMake().toString().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
                    newVehicleList.add(vehicle);
                }
            }

            if (searchBox.getValue().isEmpty()){
                grid.setItems(vehicleList);
                return;
            }

            grid.setItems(newVehicleList);
        });

//        searchBox.addValueChangeListener(e -> {
//            List<Vehicle> newVehicleList = new ArrayList<>();
//            for (Vehicle vehicle : vehicleList) {
//                if (vehicle.getPlateNumber().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
//                    newVehicleList.add(vehicle);
//                }
//                if (vehicle.getMake().toString().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
//                    newVehicleList.add(vehicle);
//                }
//            }
//
//            if (searchBox.getValue().isEmpty()){
//                grid.setItems(vehicleList);
//                return;
//            }
//
//            grid.setItems(newVehicleList);
//        });

        // Handle changes in the value
        searchBox.setValueChangeMode(ValueChangeMode.EAGER);

//        searchBox.addValueChangeListener(event ->
//                Notification.show("You searched for: " + event.getValue()));

        searchButton.getElement().getStyle().set("background-color", "lightgreen").set("color", "white");

        DatePicker pickUpDatePicker = new DatePicker("Enter Pick Up Date");
        DatePicker dropOffDatePicker = new DatePicker("Enter Drop Off Date");

        pickUpDatePicker.setMin(LocalDate.now());
        dropOffDatePicker.setMin(LocalDate.now());

        pickUpDatePicker.setPlaceholder("Enter Pick Up Date");
        dropOffDatePicker.setPlaceholder("Enter Drop Off Date");

        H6 searchTitle = new H6("Search");
        H6 bookVehicleTitle = new H6("Book a Vehicle");

        bookVehicleTitle.getElement().getStyle().set("margin", "0px").set("padding", "0px").set("padding-top", "20px");
        searchTitle.getElement().getStyle().set("margin", "0px").set("padding", "0px").set("padding-top", "20px");

        HorizontalLayout searchVehicleContainer = new HorizontalLayout(searchBox, searchButton);

        searchVehicleContainer.setVerticalComponentAlignment(Alignment.CENTER, searchBox);
        searchVehicleContainer.setVerticalComponentAlignment(Alignment.END, searchButton);
        searchVehicleContainer.getElement().getStyle().set("margin", "0px");


        grid.setItems(vehicleList);
        add(headingTitle);
        add(searchTitle);
        add(searchVehicleContainer);

        Button bookVehicleButton = new Button("Book Vehicle", event -> {
            if (selectedVehicle == null) {
                Notification.show("Please select a Vehicle first!");
                return;
            }
            if (pickUpDatePicker.getValue() == null) {
                Notification.show("Please select a pick up date!");
                return;
            }
            if (dropOffDatePicker.getValue() == null) {
                Notification.show("Please select a drop off date!");
                return;
            }

            LocalDate pickUpDateVal = pickUpDatePicker.getValue();
            LocalDate dropOffDateVal = dropOffDatePicker.getValue();

            if (dropOffDateVal.isBefore(pickUpDateVal)){
                Notification.show("Pick Up date cannot be greater than Drop Off Date!");
                return;
            }

            Date pickUpDate = new Date(pickUpDateVal.getDayOfMonth(), pickUpDateVal.getMonthValue(), pickUpDateVal.getYear());
            Date dropOffDate = new Date(dropOffDateVal.getDayOfMonth(), dropOffDateVal.getMonthValue(), dropOffDateVal.getYear());
            Schedule schedule = new Schedule(pickUpDate, dropOffDate);
            Booking booking = new Booking(schedule, selectedVehicle.getPlateNumber());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:7000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            VehicleManagerRepository service = retrofit.create(VehicleManagerRepository.class);


            try {
                Response<BookingStatus> bookingStatusResponse =  service.postBookingDetails(booking).execute();
                assert bookingStatusResponse.body() != null;
                if (bookingStatusResponse.body().isSuccess()) {
                    Notification.show("Successfully Booked Vehicle!");
                    bookingList.add(booking);
//                    grid.setItems(vehicleList);
                    grid.getDataProvider().refreshAll();


                } else {
                    Notification.show("This vehicle has already been booked for the given dates!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        });


        HorizontalLayout bookingDataContainer = new HorizontalLayout(pickUpDatePicker, dropOffDatePicker, bookVehicleButton);

        bookingDataContainer.setVerticalComponentAlignment(Alignment.CENTER, pickUpDatePicker);
        bookingDataContainer.setVerticalComponentAlignment(Alignment.CENTER, pickUpDatePicker);
        bookingDataContainer.setVerticalComponentAlignment(Alignment.END, bookVehicleButton);
        bookingDataContainer.getElement().getStyle().set("margin", "0px");


        add(bookVehicleTitle);
        add(bookingDataContainer);
        add(grid);


        grid.addItemClickListener(
                event -> {
                    AtomicReference<Vehicle> selectedVehicleRef = new AtomicReference<>();
                    selectedVehicleRef.set(event.getItem());
                    selectedVehicle = selectedVehicleRef.get();
                });


    }
}