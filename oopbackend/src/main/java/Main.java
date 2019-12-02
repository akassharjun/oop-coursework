import com.google.gson.Gson;
import io.javalin.Javalin;
import manager.DatabaseManager;
import model.Booking;
import model.BookingStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        DatabaseManager databaseManager = new DatabaseManager();

        Gson gson = new Gson();

        app.get("/vehicles", ctx -> ctx.result(gson.toJson(databaseManager.getAllVehicles())));

        app.post("/booking", ctx -> {
            Booking booking = gson.fromJson(ctx.body(), Booking.class);


            List<Booking> bookingsList = databaseManager.getAllBooking();

            if (bookingsList.isEmpty()) {
                ctx.result(gson.toJson(new BookingStatus(true)));
                databaseManager.saveBookingDetails(booking);
                return;
            }

            boolean isAvailable = true;

            for (Booking bookingDetail : bookingsList) {
                if (bookingDetail.getPlateNumber().equals(booking.getPlateNumber())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date newPickUpDate = sdf.parse(booking.getSchedule().getPickUpDate().toString());
                        Date currentDropOffDate = sdf.parse(bookingDetail.getSchedule().getDropOffDate().toString());

                        if (newPickUpDate.after(currentDropOffDate)) {
                            isAvailable = true;
                        } else {
                            isAvailable = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isAvailable) {
                ctx.result(gson.toJson(new BookingStatus(true)));
                databaseManager.saveBookingDetails(booking);
            } else {
                ctx.result(gson.toJson(new BookingStatus(false)));
            }
        });

        app.get("/booking", ctx -> {
            ctx.result(gson.toJson(databaseManager.getAllBooking()));
        });
    }
}