package com.vaadin.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.vaadin.example.manager.DatabaseManager;
import com.vaadin.example.model.*;
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
    private Vehicle selectedVehicle = null;

    private void fetchAllVehicles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:7000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VehicleManagerRepository service = retrofit.create(VehicleManagerRepository.class);

        service.getAllCars().enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                assert response.body() != null;
                vehicleList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });

        service.getAllMotorbikes().enqueue(new Callback<List<Motorbike>>() {
            @Override
            public void onResponse(Call<List<Motorbike>> call, Response<List<Motorbike>> response) {
                assert response.body() != null;
                vehicleList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Motorbike>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    public MainView() {


        fetchAllVehicles();


        H1 headingTitle = new H1("Westminster Vehicle Rental Manager");
        headingTitle.getElement().getStyle().set("margin", "0px");


        Grid<Vehicle> grid = new Grid<>(Vehicle.class);
        grid.setColumns("make", "plateNumber", "transmission", "rate");
        grid.setColumnReorderingAllowed(true);
        grid.setItemDetailsRenderer(new ComponentRenderer<>(vehicle -> {
            VerticalLayout layout = new VerticalLayout();
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                VerticalLayout verticalLayoutOne = new VerticalLayout(new H6("Doors"),new Label("" + car.getNumberOfDoors()));
                Icon yes = new Icon(VaadinIcon.CHECK);
                yes.setColor("lightgreen");

                Icon no = new Icon(VaadinIcon.CLOSE);
                no.setColor("lightred");

                VerticalLayout verticalLayoutTwo = new VerticalLayout(new H6("SunRoof"), (car.isHasSunRoof() ?  yes : no));

                HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutOne, verticalLayoutTwo);

                layout.add(horizontalLayout);
            } else {
                Motorbike motorbike = (Motorbike) vehicle;
                String standType = motorbike.getStandType().toString().substring(0, 1).toUpperCase() + motorbike.getStandType().toString().substring(1).toLowerCase();
                VerticalLayout verticalLayoutOne = new VerticalLayout(new H6("Stand Type"),new Label("" + standType));
                Icon yes = new Icon(VaadinIcon.CHECK);
                yes.setColor("lightgreen");

                Icon no = new Icon(VaadinIcon.CLOSE);
                no.setColor("lightred");

                VerticalLayout verticalLayoutTwo = new VerticalLayout(new H6("PEDALS"), (motorbike.isHasPedals() ?  yes : no));

                HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayoutOne, verticalLayoutTwo);

                layout.add(horizontalLayout);
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
            grid.setItems(newVehicleList);
        });

        searchBox.addValueChangeListener(e -> {
            System.out.println(e);
            List<Vehicle> newVehicleList = new ArrayList<>();
            for (Vehicle vehicle : vehicleList) {
                if (vehicle.getPlateNumber().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
                    newVehicleList.add(vehicle);
                }
                if (vehicle.getMake().toString().toLowerCase().contains(searchBox.getValue().toLowerCase())) {
                    newVehicleList.add(vehicle);
                }
            }
            grid.setItems(newVehicleList);
        });

        // Handle changes in the value
        searchBox.setValueChangeMode(ValueChangeMode.EAGER);

        searchBox.addValueChangeListener(event ->
                Notification.show("You searched for: " + event.getValue()));

        searchButton.getElement().getStyle().set("background-color", "lightgreen").set("color", "white");

        DatePicker pickUpDatePicker = new DatePicker("Enter Pick Up Date");
        DatePicker dropOffDatePicker = new DatePicker("Enter Drop Off Date");

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
            System.out.println(selectedVehicle);
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

            Notification.show("Successfully Booked Vehicle!");

            LocalDate pickUpDateVal = pickUpDatePicker.getValue();
            LocalDate dropOffDateVal = dropOffDatePicker.getValue();

            Date pickUpDate = new Date(pickUpDateVal.getDayOfMonth(), pickUpDateVal.getMonthValue(), pickUpDateVal.getYear());
            Date dropOffDate = new Date(dropOffDateVal.getDayOfMonth(), dropOffDateVal.getMonthValue(), dropOffDateVal.getYear());
            Schedule schedule = new Schedule(pickUpDate, dropOffDate);
            Booking booking = new Booking(schedule, selectedVehicle);

            System.out.println(booking.toString());
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
                    System.out.println(event.getItem());
                });



    }
}