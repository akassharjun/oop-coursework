package controller;

import com.google.gson.Gson;
import de.vandermeer.asciitable.AsciiTable;
import manager.DatabaseManager;
import model.*;
import utils.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {
    static List<Vehicle> vehicleList = new ArrayList<>();
    private static final int MAX_LOT = 50;
    private static DatabaseManager databaseManager;

    public WestminsterRentalVehicleManager() {
        databaseManager = new DatabaseManager();

        vehicleList.addAll(databaseManager.getAllVehicles());
    }

    /**
     * Displays the menu options for the WestminsterRentalVehicleManager
     */
    public void displayMenu() {
        System.out.println("Main Menu" +
                "\n1. Add Vehicle" +
                "\n2. Delete Vehicle" +
                "\n3. Print Vehicle List" +
                "\n4. Write/Save Vehicle List" +
                "\n5. Exit"
        );
    }

    /**
     * Adds a vehicle to the Vehicle List managed by the rental store
     */
    @Override
    public void addVehicle() {
        if (vehicleList.size() >= 50) {
            System.out.println("No more lots!");
            return;
        }

        String vehicleType = Utilities.getConditionalString("Do you want to add a Car or a Motorbike? (C/M)",
                Arrays.asList("C", "M"));

        Make make;

        if (vehicleType.equals("C")) {
            String makeValue = Utilities.getConditionalString("Enter make", Stream.of(CarMake.values())
                    .map(CarMake::name)
                    .collect(Collectors.toList()));
            make = Make.valueOf(makeValue);
        } else {
            String makeValue = Utilities.getConditionalString("Enter make (Bike)", Stream.of(BikeMake.values())
                    .map(BikeMake::name)
                    .collect(Collectors.toList()));
            make = Make.valueOf(makeValue);
        }


        BigDecimal rate = (BigDecimal) Utilities.getNumberInput("Enter rate", BigDecimal.class);

        String plateNumber = Utilities.getPlainString("Enter the plate number");

        String transmissionValue = Utilities.getConditionalString("Enter the transmission",
                Stream.of(Transmission.values()).map(Transmission::name).collect(Collectors.toList()));

        Transmission transmission = Transmission.valueOf(transmissionValue);


        if (vehicleType.equals("C")) {
            int numberOfDoors = (Integer) Utilities.getNumberInput("Enter the number of doors", Integer.class);

            String sunroof = Utilities.getConditionalString("Does the car have a sunroof? (Y/N)",
                    Arrays.asList("Y", "N"));

            Car car = new Car(make, plateNumber, transmission, rate, numberOfDoors, sunroof.equals("Y"));
            vehicleList.add(car);
            databaseManager.insertVehicle(car);
        } else {
            String pedal = Utilities.getConditionalString("Does the motorbike have pedals? (Y/N)",
                    Arrays.asList("Y", "N"));

            String standTypeValue = Utilities.getConditionalString("Enter stand type",
                    Stream.of(StandType.values())
                            .map(StandType::name)
                            .collect(Collectors.toList()));

            StandType standType = StandType.valueOf(standTypeValue);

            Motorbike motorbike = new Motorbike(make, plateNumber, transmission, rate, pedal.equals("Y"), standType);
            vehicleList.add(motorbike);
            databaseManager.insertVehicle(motorbike);
        }
    }

    /**
     * Deletes a vehicle to the Vehicle List managed by the rental store
     */
    @Override
    public void deleteVehicle() {
        printVehicleStockList();
        System.out.println("Which vehicle would you like to delete? : ");
        String plateNumber = new Scanner(System.in).nextLine().toLowerCase();

        Optional<Vehicle> vehicle = vehicleList.stream().filter(v -> v.getPlateNumber().toLowerCase().equals(plateNumber)).findAny();

        if (vehicle.isPresent()) {
            System.out.println(vehicle.get());

            vehicleList.remove(vehicle.get());
            databaseManager.deleteVehicle(vehicle.get().getId());

            System.out.println("Removed Vehicle");
        } else {
            System.out.println("Incorrect Number plate");
        }


        int remainingSlots = 50 - vehicleList.size();
        System.out.println("Remaining slots : " + remainingSlots);
    }

    /**
     * Prints the list of vehicles available in the rental store
     */
    @Override
    public void printVehicleStockList() {

        vehicleList.sort(new SortByMake());
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow("Type", "Make", "Plate No", "Transmission", "Rate", "Doors", "Sun Roof", "Stand Type", "Pedals");
        at.addRule();

        for (Vehicle vehicle : vehicleList) {
            if (vehicle instanceof Car) {
                at.addRow("Car", vehicle.getMake(), vehicle.getPlateNumber(), vehicle.getTransmission(), vehicle.getRate(), ((Car) vehicle).getNumberOfDoors(), ((Car) vehicle).hasSunRoof() ? "Has" : "Doesn't have", "-", "-");
            } else {
                at.addRow("Motorbike", vehicle.getMake(), vehicle.getPlateNumber(), vehicle.getTransmission(), vehicle.getRate(), "-", "-", ((Motorbike) vehicle).getStandType(), ((Motorbike) vehicle).isHasPedals() ? "Has" : "Doesn't have");
            }
            at.addRule();
        }


        System.out.println(at.render(120));
    }

    /**
     * Saves the list of vehicles available in the rental store into a json file
     */
    @Override
    public void saveVehicleStockList() {
        File file = new File("vehicle.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            try (PrintWriter printWriter = new PrintWriter(fileWriter, true)) {
                Gson gson = new Gson();
                printWriter.write(gson.toJson(vehicleList));
                System.out.println("Successfully saved the data to the list!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Books a vehicle
     */
    public static void bookVehicle(Schedule schedule, Vehicle vehicle) {
        databaseManager.saveBookingDetails(new Booking(schedule, vehicle.getPlateNumber()));
    }

    public static class SortByMake implements Comparator<Vehicle> {
        // Used for sorting in ascending order of
        // roll name
        public int compare(Vehicle a, Vehicle b) {
            return a.getMake().toString().compareTo(b.getMake().toString());
        }
    }


}
