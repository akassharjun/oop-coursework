package manager;

import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import model.Booking;
import model.Car;
import model.Motorbike;
import org.bson.types.ObjectId;

import java.awt.print.Book;
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
     * Saves a car to the database
     * @param car the car
     */
    public void insertCar(Car car)  {
        datastore.save(car);
    }

    /**
     * Saves a motorbike to the database
     * @param motorbike the motorbike
     */
    public void insertMotorbike(Motorbike motorbike) {
        datastore.save(motorbike);
    }

    /**
     * Deletes a model.model.Car from the database
     * @param itemId the object id of the car item
     */
    public void deleteCar(ObjectId itemId){
        final Query<Car> query = datastore.createQuery(Car.class).filter("_id ==", itemId);
        datastore.delete(query);
    }

    /**
     * Deletes a model.model.Motorbike from the database
     * @param itemId the object id of the motorbike item
     */
    public void deleteMotorbike(ObjectId itemId) {
        final Query<Motorbike> query = datastore.createQuery(Motorbike.class).filter("_id ==", itemId);
        datastore.delete(query);
    }

    /**
     * Retrieves all the Cars from database
     * @return List<model.model.Car> list of cars
     */
    public List<Car> getAllCars() {
        final Query<Car> query = datastore.createQuery(Car.class);
        return query.find().toList();
    }

    /**
     * Retrieves all the Motorbikes from database
     * @return List<model.model.Motorbike> list of motorbikes
     */
    public List<Motorbike> getAllMotorbikes() {
        final Query<Motorbike> query = datastore.createQuery(Motorbike.class);
        return query.find().toList();
    }

    public void saveBookingDetails(Booking booking){
    datastore.save(booking);
    }

    public List<Booking> getAllBookingDetails() {
        final Query<Booking> query = datastore.createQuery(Booking.class);
        return query.find().toList();
    }
}
