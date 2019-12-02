package controller;

import com.google.gson.Gson;
import de.vandermeer.asciitable.AsciiTable;
import manager.DatabaseManager;
import model.*;
import utils.TableList;
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

        for (int i = 0; i < 50; i++) {

            Make carMakeValue = Make.getRandomMake();

            while (carMakeValue.toString().equals("DUCATTI") || carMakeValue.toString().equals("HONDA") || carMakeValue.toString().equals("BAJAJ") || carMakeValue.toString().equals("SCOOTYPEP") || carMakeValue.toString().equals("KAWASAKI") || carMakeValue.toString().equals("YAMAHA")) {
                carMakeValue = Make.getRandomMake();
            }

            Make motorbikeValue = Make.getRandomMake();

            while (!motorbikeValue.toString().equals("DUCATTI") && !motorbikeValue.toString().equals("HONDA") && !motorbikeValue.toString().equals("BAJAJ") && !motorbikeValue.toString().equals("SCOOTYPEP") && !motorbikeValue.toString().equals("KAWASAKI") && !motorbikeValue.toString().equals("YAMAHA")) {
                motorbikeValue = Make.getRandomMake();
            }

            Transmission transmission = Transmission.getRandomTransmission();

            StandType standType = StandType.getRandomStandType();

            Car car = new Car(carMakeValue, "C" + i, transmission, new BigDecimal(new Random().nextInt(10000 - 50) + 50), new Random().nextInt(4 - 2) + 2, true);
            Motorbike motorbike = new Motorbike(motorbikeValue, "B" + i, transmission, new BigDecimal(new Random().nextInt(10000 - 50) + 50), true, standType);
            vehicleList.add(car);
            vehicleList.add(motorbike);
//            databaseManager.insertCar(car);
//            databaseManager.insertMotorbike(motorbike);
        }
        vehicleList.addAll(databaseManager.getAllCars());
        vehicleList.addAll(databaseManager.getAllMotorbikes());
    }

    public void displayMenu() {
        System.out.println("Main Menu" +
                "\n1. Add Vehicle" +
                "\n2. Delete Vehicle" +
                "\n3. Print Vehicle List" +
                "\n4. Write/Save Vehicle List" +
                "\n5. Open Customer Console" +
                "\n6. Exit"
        );
    }

    @Override
    public void addVehicle() {
        if (vehicleList.size() > 50) {
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
            databaseManager.insertCar(car);
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
            databaseManager.insertMotorbike(motorbike);
        }
    }

    @Override
    public void deleteVehicle() {
        for (int i = 0; i < vehicleList.size(); i++) {
            System.out.println(i + 1 + " " + vehicleList.get(i).toString());
        }
        System.out.println("Which vehicle would you like to delete? : ");
        String plateNumber = new Scanner(System.in).nextLine();

        Optional<Vehicle> vehicle = vehicleList.stream().filter(v -> v.getPlateNumber().equals(plateNumber)).findAny();


        if (vehicle.isPresent()) {
            System.out.println(vehicle.get());

            vehicleList.remove(vehicle.get());

            System.out.println("Removed Vehicle");
        } else {
            System.out.println("Incorrect Number plate");
        }


        int remainingSlots = 50 - vehicleList.size();
        System.out.println("Remaining slots : " + remainingSlots);
    }

    @Override
    public void printVehicleStockList() {

        vehicleList.sort(new SortByMake());
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow("Type", "Make", "Plate No", "Transmission", "Rate", "Doors", "Sun Roof", "Stand Type", "Pedals");
        at.addRule();

        for (Vehicle vehicle : vehicleList) {
            if (vehicle instanceof Car) {
                at.addRow("Car", vehicle.getMake(), vehicle.getPlateNumber(), vehicle.getTransmission(), vehicle.getRate(), ((Car) vehicle).getNumberOfDoors(), ((Car) vehicle).hasSunRoof()  ? "Has" : "Doesn't have", "-", "-");
            } else {
                at.addRow("Motorbike", vehicle.getMake(), vehicle.getPlateNumber(), vehicle.getTransmission(), vehicle.getRate(), "-", "-", ((Motorbike) vehicle).getStandType(), ((Motorbike) vehicle).isHasPedals() ? "Has" : "Doesn't have");
            }
            at.addRule();
        }


        System.out.println(at.render(120));
    }

    @Override
    public void saveVehicleStockList() {
        File file = new File("vehicle.txt");
        try (FileWriter fileWriter = new FileWriter(file)) {
            try (PrintWriter printWriter = new PrintWriter(fileWriter, true)) {
                Gson gson = new Gson();
                printWriter.write(gson.toJson(vehicleList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class SortByMake implements Comparator<Vehicle> {
        // Used for sorting in ascending order of
        // roll name
        public int compare(Vehicle a, Vehicle b) {
            return a.getMake().toString().compareTo(b.getMake().toString());
        }
    }

    public static void bookVehicle(Schedule schedule, Vehicle vehicle) {
        databaseManager.saveBookingDetails(new Booking(schedule, vehicle));
    }

}
