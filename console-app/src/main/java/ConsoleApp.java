import controller.WestminsterRentalVehicleManager;
import javafx.application.Platform;
import utils.Utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Console Application
 * The base class for the console application
 *
 * @author Akassharjun Shamugarajah
 */


public class ConsoleApp {

    /**
     * The beginning point for the application
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        WestminsterRentalVehicleManager westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();
        int option = 0;

//        Thread backgroundThread = new Thread(GraphicalUserInterface::main);
//        backgroundThread.start();

        while (option != 6) {
            westminsterRentalVehicleManager.displayMenu();
            option = (Integer) Utilities.getNumberInput("$ Enter an option", Integer.class);

            switch (option) {
                case 1:
                    westminsterRentalVehicleManager.addVehicle();
                    break;
                case 2:
                    westminsterRentalVehicleManager.deleteVehicle();
                    break;
                case 3:
                    westminsterRentalVehicleManager.printVehicleStockList();
                    break;
                case 4:
                    westminsterRentalVehicleManager.saveVehicleStockList();
                    break;
                case 5:
                    System.out.println("You chose to exit the program. Have a nice day! :)\nQuitting....");
                    break;
            }
            System.out.println();
        }
    }
}
