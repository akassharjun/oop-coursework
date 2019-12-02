import controller.WestminsterRentalVehicleManager;
import javafx.application.Platform;
import utils.Utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ConsoleApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
        WestminsterRentalVehicleManager westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();
        int option = 0;
//        GraphicalUserInterface.main();
//        Thread backgroundThread = new Thread(GraphicalUserInterface::main);
//        backgroundThread.start();
//        Platform.runLater(GraphicalUserInterface::displayStage);

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
                    File reactAppDirectory = new File("/Users/admin/Code/Projects/oop-gui/");
                    Process process = new ProcessBuilder("npm", "start")
                            .directory(reactAppDirectory)
                            .start();
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI("http://localhost:3000/"));
                    }
                    break;
                case 6:
                    System.out.println("You chose to exit the program. Have a nice day! :)\nQuitting....");
                    break;
            }
            System.out.println();
        }
    }
}
