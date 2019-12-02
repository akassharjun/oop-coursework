import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * GraphicalUserInterface
 * The base class for the graphical user interface
 *
 * @author Akassharjun Shamugarajah
 */


public class  GraphicalUserInterface extends Application {
    private static Stage primaryStage;

    /**
     * The starting point of the Application GUI
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Setting this to implicit exit means it won't quit the program even
        // when the user clicks on the exit button.
        Platform.setImplicitExit(false);

        // creating the url path to the FXML file
        URL url = new File("src/main/resources/view/table_view.fxml").toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("Table View of Music Items");
        // setting the view to the scene
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
        GraphicalUserInterface.primaryStage = primaryStage;
    }

    /**
     * To begin the Application GUI
     */
    public static void main() {
        launch();
    }

    /**
     * To display the stage
     */
    public static void displayStage() {
        primaryStage.show();
    }
}
