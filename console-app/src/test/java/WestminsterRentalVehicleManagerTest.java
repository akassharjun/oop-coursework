import controller.WestminsterRentalVehicleManager;
import manager.DatabaseManager;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterRentalVehicleManagerTest {
    private WestminsterRentalVehicleManager westminsterRentalVehicleManager;
    private DatabaseManager databaseManager;


    @BeforeEach
    void initEach() throws UnknownHostException {
        westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();
        databaseManager = new DatabaseManager();
    }

    @Test
    void addVehicleTest() {
        Car car = new Car(Make.AUDI, "CAZ 2345", Transmission.MANUAL, new BigDecimal(300), 4, true);
        Motorbike motorbike = new Motorbike(Make.DUCATTI, "CAZ 3245", Transmission.AUTOMATIC, new BigDecimal(250), true, StandType.SINGLE);

        assertNotNull(car);
        assertNotNull(motorbike);
    }

    @Test
    void deleteVehicleTest() {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        Car car = new Car(Make.AUDI, "CAZ 2345", Transmission.MANUAL, new BigDecimal(300), 4, true);
        Motorbike motorbike = new Motorbike(Make.DUCATTI, "CAZ 3245", Transmission.AUTOMATIC, new BigDecimal(250), true, StandType.SINGLE);


        vehicles.add(car);
        vehicles.remove(car);

        vehicles.add(motorbike);
        vehicles.remove(motorbike);

        assertTrue(vehicles.isEmpty());
    }


    @Test
    void printVehicleListTest() {
        List<Vehicle> vehicleList = new ArrayList<>();
        List<Vehicle> sortedVehicleList = new ArrayList<>();

        Car car = new Car(Make.AUDI, "CAZ 2345", Transmission.MANUAL, new BigDecimal(300), 4, true);
        Motorbike motorbike = new Motorbike(Make.DUCATTI, "CAZ 3245", Transmission.AUTOMATIC, new BigDecimal(250), true, StandType.SINGLE);

        vehicleList.add(car);
        vehicleList.add(motorbike);

        vehicleList.sort(new WestminsterRentalVehicleManager.SortByMake());
        sortedVehicleList.add(car);
        sortedVehicleList.add(motorbike);

        assertEquals(vehicleList, sortedVehicleList);
    }

}