package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * TableController
 * The MV "C", controller part of the program, it controls the GUI elements.
 *
 * @author Akassharjun Shanmugarajah
 */
public class TableController implements Initializable {

    @FXML
    private TableView<Vehicle> tableView;
    @FXML
    private TableColumn<Vehicle, String> plateNumber;
    @FXML
    private TableColumn<Vehicle, Make> make;
    @FXML
    private TableColumn<Vehicle, Transmission> transmission;
    @FXML
    private TableColumn<Vehicle, BigDecimal> rate;
    @FXML
    private TableColumn<Vehicle, Integer> numberOfDoors;
    @FXML
    private TableColumn<Vehicle, Boolean> hasSunRoof;
    @FXML
    private TableColumn<Vehicle, StandType> standType;
    @FXML
    private TableColumn<Vehicle, Boolean> hasPedals;
    @FXML
    private TextField searchBox;
    @FXML
    private Button searchButton;
    @FXML
    public Button bookButton;
    @FXML
    public DatePicker pickUpDate;
    @FXML
    public DatePicker dropOffDate;


    /**
     * To initialize the table columns with the proper cell names to map correctly
     * with the MusicItem class
     *
     * @param location  unused
     * @param resources unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initializing the table columns
        plateNumber.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        make.setCellValueFactory(new PropertyValueFactory<>("make"));
        transmission.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        numberOfDoors.setCellValueFactory(new PropertyValueFactory<>("numberOfDoors"));
        hasSunRoof.setCellValueFactory(new PropertyValueFactory<>("hasSunRoof"));
        standType.setCellValueFactory(new PropertyValueFactory<>("standType"));
        hasPedals.setCellValueFactory(new PropertyValueFactory<>("hasPedals"));

        this.addMusicItems();
    }

    /**
     * To add the music items to the table view.
     */
    private void addMusicItems() {
        System.out.println(WestminsterRentalVehicleManager.vehicleList);
        WestminsterRentalVehicleManager.vehicleList.forEach((vehicle -> {
//            tableView.getItems().add(vehicle);

            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                tableView.getItems().add(car);
            } else {
                Motorbike motorbike = (Motorbike) vehicle;
                tableView.getItems().add(motorbike);
            }
        }));
    }

    /**
     * To filter the contents of the table when the user searches for a title.
     */
    @FXML
    public void onSearchButtonClick(MouseEvent event) {
        String text = searchBox.getText();
        // clearing all the previous entries
        tableView.getItems().clear();
        WestminsterRentalVehicleManager.vehicleList.forEach((vehicle -> {
            // checking if what the user entered is in the title and
            // adding it to the table
            if (vehicle.getPlateNumber().toLowerCase().contains(text.toLowerCase())) {
                if (vehicle instanceof Car) {
                    Car car = (Car) vehicle;
                    tableView.getItems().add(car);
                } else {
                    Motorbike motorbike = (Motorbike) vehicle;
                    tableView.getItems().add(motorbike);
                }
            }
        }));
    }

    @FXML
    public void onBookButtonPressed(MouseEvent event) {
        Vehicle vehicle = tableView.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.NONE);


        if (vehicle == null) {
            // set alert type
            a.setContentText("You haven't selected a vehicle to book!");
            a.setAlertType(Alert.AlertType.ERROR);

            // show the dialog
            a.show();
            return;
        }

        LocalDate pickUpDateValue = pickUpDate.getValue();

        if (pickUpDateValue == null) {
            // set alert type
            a.setContentText("You haven't entered a pick up date!");
            a.setAlertType(Alert.AlertType.ERROR);

            // show the dialog
            a.show();
            return;
        }

        LocalDate dropOffDate = pickUpDate.getValue();


        if (dropOffDate == null) {
            // set alert type
            a.setContentText("You haven't entered a drop off date!");
            a.setAlertType(Alert.AlertType.ERROR);

            // show the dialog
            a.show();
            return;
        }

        Schedule schedule = new Schedule(new Date(pickUpDateValue.getDayOfMonth(), pickUpDateValue.getMonthValue(), pickUpDateValue.getYear()), new Date(dropOffDate.getDayOfMonth(), dropOffDate.getMonthValue(), dropOffDate.getYear()));

        WestminsterRentalVehicleManager.bookVehicle(schedule, vehicle);
    }

}
