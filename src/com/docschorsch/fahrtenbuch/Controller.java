package com.docschorsch.fahrtenbuch;

import datamodel.Trip;
import datamodel.TripData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

//    public static ArrayList<Trip> trips = new ArrayList<Trip>();
    @FXML
    private ListView<Trip> tripListView;

    @FXML
    private TextField startLocationField;
    @FXML
    private TextField endLocationField;
    @FXML
    private TextField distanceField;

    @FXML
    private TextField tripBeginHour;
    @FXML
    private TextField tripBeginMinute;
    @FXML
    private TextField tripEndHour;
    @FXML
    private TextField tripEndMinute;

    @FXML
    private DatePicker tripBeginDatePicker;
    @FXML
    private DatePicker tripEndDatePicker;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private Button saveEdits;


    private SortedList<Trip> tripSortedList;


    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {

//        Trip trip = new Trip("muc", "ber", 600, LocalDateTime.now(),LocalDateTime.now());
//        Trip trip2 = new Trip("ber", "muc", 20, LocalDateTime.now(),LocalDateTime.now());
//        Trip trip3 = new Trip("bra", "bar", 300, LocalDateTime.now(),LocalDateTime.now());

//        ArrayList<Trip> trips = new ArrayList<>();
//        TripData.getInstance().addTrip(trip);
//        TripData.getInstance().addTrip(trip2);
//        TripData.getInstance().addTrip(trip3);

//        trips.add(trip2);
//        trips.add(trip3);

        saveEdits.setOnAction(event -> addTrip(2));

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Trip trip = tripListView.getSelectionModel().getSelectedItem();
                deleteTrip(trip);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        tripListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Trip>() {
            @Override
            public void changed(ObservableValue<? extends Trip> observableValue, Trip trip, Trip t1) {
                if(t1 != null) {
                    Trip selTrip = tripListView.getSelectionModel().getSelectedItem();
                    startLocationField.setText(selTrip.getStartLocation());
                    endLocationField.setText(selTrip.getEndLocation());
                    distanceField.setText(Integer.toString(selTrip.getDistance()));
                    tripBeginDatePicker.setValue(LocalDate.from(selTrip.getTripBegin()));
                    tripEndDatePicker.setValue(LocalDate.from(selTrip.getTripEnd()));
                    tripBeginHour.setText(Integer.toString(selTrip.getTripBegin().getHour()));
                    tripBeginMinute.setText(Integer.toString(selTrip.getTripBegin().getMinute()));
                    tripEndHour.setText(Integer.toString(selTrip.getTripEnd().getHour()));
                    tripEndMinute.setText(Integer.toString(selTrip.getTripEnd().getMinute()));

                }
            }
        });

        tripSortedList = new SortedList<Trip>(TripData.getInstance().getTrips(), new Comparator<Trip>() {
            @Override
            public int compare(Trip o1, Trip o2) {
                return o1.getTripEnd().compareTo(o2.getTripEnd());
            }
        });

        tripListView.setItems(tripSortedList);
        tripListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tripListView.getSelectionModel().selectFirst();

        tripListView.setCellFactory(new Callback<ListView<Trip>, ListCell<Trip>>() {
            @Override
            public ListCell<Trip> call(ListView<Trip> tripListView) {
                ListCell<Trip> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Trip trip, boolean empty) {
                        super.updateItem(trip, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(trip.toString());
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }

                        });
                return cell;
            }
        });
    }

    // overloaded add method to cater for default addTrip() called in fxml
    public void addTrip() {
        addTrip(1);
    }

    public void addTrip(int addOrEdit) {

        String newStartLocation = startLocationField.getText();
        String newEndLocation = endLocationField.getText();
        int newDistance = Integer.parseInt(distanceField.getText().isEmpty() ? "99" : distanceField.getText());
        LocalDateTime tripBeginDateTime;
        LocalDateTime tripEndDateTime;

//        set default of Hour, Min Textfields to 00
//        tripBeginHour.setText("00");
//        tripBeginMinute.setText("00");
//        tripEndHour.setText("00");
//        tripEndMinute.setText("00");

        LocalTime tripBeginTime;
        LocalTime tripEndTime;

        // datePicker must not be null/default
        if (tripBeginDatePicker.getValue()!=null && tripEndDatePicker.getValue()!=null) {
            tripBeginTime = LocalTime.parse(returnValidatedTimeEntry(tripBeginHour) + ":" + returnValidatedTimeEntry(tripBeginMinute));
            tripBeginDateTime = LocalDateTime.of(tripBeginDatePicker.getValue(), tripBeginTime);
            tripEndTime = LocalTime.parse(returnValidatedTimeEntry(tripEndHour) + ":" + returnValidatedTimeEntry(tripEndMinute));
            tripEndDateTime = LocalDateTime.of(tripEndDatePicker.getValue(), tripEndTime);
            // test if trip begin is past trip end, if so, swap
            if ( tripBeginDateTime.compareTo(tripEndDateTime) > 0 ) {
                LocalDateTime changedNewBegin = tripEndDateTime;
                tripEndDateTime = tripBeginDateTime;
                tripBeginDateTime = changedNewBegin;
                tripBeginDatePicker.setValue(LocalDate.from(tripBeginDateTime));
                tripEndDatePicker.setValue(LocalDate.from(tripEndDateTime));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Changed Trip Begin <-> End Time");
                alert.setContentText("Trip Begin entered is past Trip End, Dates have been swapped!");
                Optional<ButtonType> result = alert.showAndWait();
            }

        } else {
            return;
        }

        Trip trip = new Trip(newStartLocation,newEndLocation,newDistance,tripBeginDateTime,tripEndDateTime);
        if((newStartLocation == null) || (newEndLocation == null) || (newDistance == 0) ) {
            System.out.println("Error - trying to add empty or 0 field!");
            // edit if "save edit" was pressed
        } else if (addOrEdit == 2) {
            Trip selectedTrip = tripListView.getSelectionModel().getSelectedItem();
            TripData.getInstance().editTrip(selectedTrip, trip);
        } else {
            TripData.getInstance().addTrip(trip);
        }
        tripListView.setItems(tripSortedList);
        tripListView.getSelectionModel().select(trip);
    }


    public String returnValidatedTimeEntry(TextField textField) {
        String s;
        // test for one digit number, add leading 0
        if(textField.getText().matches("\\d")) {
            s = "0" + textField.getText();
        }
        // test for empty Strings or such containing values other than 0-9, more than 2 digits or values >23 hours / >59 minutes respectively
        else if( (!textField.getText().matches("\\d*")) || (textField.getText().length()>2) || (textField.getText().isEmpty()) ||
                ((textField.getId().contains("Hour") && Integer.parseInt(textField.getText()) >23 )) ||
                ((textField.getId().contains("Minute") && Integer.parseInt(textField.getText()) >59 )) ){
            s = "00";
        }
        else {
            s = textField.getText();
        }
        return s;
    }

    public void resetUserEntry() {
        startLocationField.clear();
        endLocationField.clear();
        distanceField.clear();
        tripBeginHour.clear();
        tripBeginMinute.clear();
        tripEndHour.clear();
        tripEndMinute.clear();
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        Trip selectedTrip = tripListView.getSelectionModel().getSelectedItem();
        if(selectedTrip != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteTrip(selectedTrip);
            }
        }
    }

    public void deleteTrip(Trip trip) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Trip");
        alert.setHeaderText("Delete Trip " + trip);
        alert.setContentText("Are you sure? Press OK to confirm or cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get()==ButtonType.OK)) {
            TripData.getInstance().deleteTripItem(trip);
        }

    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

}
