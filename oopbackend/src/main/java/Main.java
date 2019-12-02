import com.google.gson.Gson;
import io.javalin.Javalin;
import manager.DatabaseManager;
import model.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        DatabaseManager databaseManager = new DatabaseManager();
        Gson gson = new Gson();
        app.get("/cars", ctx -> ctx.result(gson.toJson(databaseManager.getAllCars())));

        app.get("/motorbikes", ctx -> ctx.result(gson.toJson(databaseManager.getAllMotorbikes())));

        app.post("/booking", ctx -> {
            Booking booking = gson.fromJson(ctx.body(), Booking.class);

            List<Booking> bookingsList = databaseManager.getAllBookingDetails();

            bookingsList.forEach(bookingDetail -> {
                if (bookingDetail.getVehicle().getPlateNumber().equals(booking.getVehicle().getPlateNumber())){
                    // Create 2 dates starts
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date newPickUpDate = sdf.parse(booking.getSchedule().getPickUpDate().toString());
                        Date currentPickUpDate = sdf.parse(bookingDetail.getSchedule().getPickUpDate().toString());

                        Date newDropOffDate = sdf.parse(booking.getSchedule().getDropOffDate().toString());
                        Date currentDropOffDate = sdf.parse(bookingDetail.getSchedule().getDropOffDate().toString());

                        if (newPickUpDate.after(currentPickUpDate) && newDropOffDate.before(currentDropOffDate)){

                        }

                        if (newPickUpDate.after(currentDropOffDate)) {

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}