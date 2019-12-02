package manager;

import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import model.*;
import org.bson.types.ObjectId;

import java.util.List;

// Reference - PP02 Assignment

/**
 * Database Manager
 * To manage all the database operations (CRUD)
 * @author Akassharjun Shamugarajah
 */


public class DatabaseManager {
    private final Morphia morphia = new Morphia();
    private final Datastore datastore = morphia.createDatastore(new MongoClient(), "westminster_rental_vehicle_manager");

    public DatabaseManager() {
        morphia.mapPackage("com.github.akassharjun.console-app.model");
        datastore.ensureIndexes();
    }


    /**
     * Saves a vehicle to the database
     * @param vehicle the vehicle
     */
    public void insertVehicle(Vehicle vehicle)  {
        datastore.save(vehicle);
    }


    /**
     * Deletes a Vehicle from the database
     * @param itemId the object id of the vehicle item
     */
    public void deleteVehicle(ObjectId itemId){
        final Query<Vehicle> query = datastore.createQuery(Vehicle.class).filter("_id ==", itemId);
        datastore.delete(query);
    }

    /**
     * Retrieves all the Vehicles from database
     * @return List<Vehicle> list of vehicles
     */
    public List<Vehicle> getAllVehicles() {
        final Query<Vehicle> query = datastore.createQuery(Vehicle.class);
        return query.find().toList();
    }

    public List<Booking> getAllBooking() {
        final Query<Booking> query = datastore.createQuery(Booking.class);
        return query.find().toList();
    }

    public void saveBookingDetails(Booking booking){
        datastore.save(booking);
    }
}
